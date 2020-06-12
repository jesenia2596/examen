package com.examen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examen.entity.PlayList;
import com.examen.repository.PlayListRepository;

@Service
public class PlayListService {

	private PlayListRepository repository;

	public PlayListService(PlayListRepository repository) {
		this.repository = repository;
	}

	public PlayList save(PlayList playList) {
		return repository.save(playList);
	}

	public List<PlayList> findAll() {
		return (List<PlayList>) repository.findAll();
	}

	public PlayList findByName(String name) {
		return repository.findByName(name);
	}

	public PlayList update(String listName, PlayList song) {
		return this.save(song);
	}

	public void delete(String name) {
		repository.deleteByName(name);
	}

}
