package com.examen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examen.entity.Song;
import com.examen.repository.SongRepository;

@Service
public class SongService {

	private SongRepository repository;

	public SongService(SongRepository repository) {
		this.repository = repository;
	}

	public Song save(Song song) {
		return repository.save(song);
	}

	public List<Song> findByPlayListName(String name) {
		return (List<Song>) repository.findByPlayListName(name);
	}
	
	public Song findByPlayListNameAndSong(String listName, String song) {
		return repository.findByPlayListName(listName, song);
	}

	public Song update(Song song) {
		return this.save(song);
	}

	public void deleteByPlayListNameAndSong(String playList, String title ) {		
		repository.deletePlayListNameAndSong(playList, title);
	}
}
