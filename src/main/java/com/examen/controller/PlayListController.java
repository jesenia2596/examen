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
import com.examen.service.PlayListService;

@RestController
@RequestMapping("api/list")
public class PlayListController {

	private PlayListService playListService;

	public PlayListController(PlayListService playListService) {
		this.playListService = playListService;
	}

	@PostMapping
	public ResponseEntity<PlayList> savePlayList(@Validated @RequestBody PlayList playList) {
		if (playList.getName() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(this.playListService.save(playList), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<PlayList>> getPlayLists() {
		return new ResponseEntity<List<PlayList>>(this.playListService.findAll(), HttpStatus.OK);
	}

	@GetMapping("{listName}")
	public ResponseEntity<PlayList> getPlayList(@PathVariable String listName) {
		PlayList playList = this.playListService.findByName(listName);
		if (playList != null) {
			return new ResponseEntity<PlayList>(playList, HttpStatus.OK);
		}
		return new ResponseEntity<PlayList>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("{listName}")
	public ResponseEntity<PlayList> updatePlayList(@PathVariable String listName,
			@Validated @RequestBody PlayList playList) {
		PlayList playListDB = this.playListService.findByName(listName);
		if (playListDB == null) {
			return new ResponseEntity<PlayList>(HttpStatus.NOT_FOUND);
		} else if (!playListDB.getName().equals(playList.getName())) {
			return new ResponseEntity<PlayList>(HttpStatus.CONFLICT);
		}
		this.playListService.update(listName, playList);
		return new ResponseEntity<PlayList>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("{listName}")
	public ResponseEntity<PlayList> deletePlayList(@PathVariable String listName) {
		PlayList playListDB = this.playListService.findByName(listName);
		if (playListDB == null) {
			return new ResponseEntity<PlayList>(HttpStatus.NOT_FOUND);
		}
		this.playListService.delete(listName);
		return new ResponseEntity<PlayList>(HttpStatus.NO_CONTENT);
	}

}
