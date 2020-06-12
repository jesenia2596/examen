package com.examen.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.entity.PlayList;
import com.examen.entity.Song;
import com.examen.service.PlayListService;
import com.examen.service.SongService;

@RestController
@RequestMapping("api/list")
public class SongController {

	private SongService songService;
	private PlayListService playListService;

	public SongController(SongService songService, PlayListService playListService) {
		this.songService = songService;
		this.playListService = playListService;

	}

	@PostMapping("{listName}/songs")
	public ResponseEntity<Song> saveSong(@PathVariable String listName, @Validated @RequestBody Song song) {
		PlayList playList = this.playListService.findByName(listName);
		if (playList == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (song.getArtist() == null || song.getAlbum() == null || song.getTitle() == null || song.getYear() < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		song = this.songService.save(song);
		playList.getSongs().add(song);
		this.playListService.update(listName, playList);
		return new ResponseEntity<>(song, HttpStatus.CREATED);
	}

	@GetMapping("{listName}/songs")
	public ResponseEntity<List<Song>> getPlayLists(@PathVariable String listName) {
		return new ResponseEntity<List<Song>>(this.songService.findByPlayListName(listName), HttpStatus.OK);
	}

	@GetMapping("{listName}/songs/{title}")
	public ResponseEntity<Song> getPlayListNameAndSong(@PathVariable String listName, @PathVariable String title) {
		Song song = this.songService.findByPlayListNameAndSong(listName, title);
		if (song != null) {
			return new ResponseEntity<Song>(song, HttpStatus.OK);
		}
		return new ResponseEntity<Song>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("{listName}/songs/{title}")
	public ResponseEntity<Song> updateSong(	@PathVariable String listName, 
											@PathVariable String title,
											@Validated @RequestBody Song song) {
		PlayList playList = this.playListService.findByName(listName);
		Song songDB = this.songService.findByPlayListNameAndSong(listName, title);
		if (playList == null || songDB == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		if (song.getArtist() == null || song.getAlbum() == null || song.getTitle() == null || song.getYear() < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		song = this.songService.update(song);
		return new ResponseEntity<>(song, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("{listName}/songs/{title}")
	public ResponseEntity<Song> deleteSong(	@PathVariable String listName, 
											@PathVariable String title) {
		PlayList playList = this.playListService.findByName(listName);
		Song songDB = this.songService.findByPlayListNameAndSong(listName, title);
		if (playList == null || songDB == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		this.songService.deleteByPlayListNameAndSong(listName, title);
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}

}
