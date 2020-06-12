package com.examen.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import com.examen.entity.PlayList;

public interface PlayListRepository extends CrudRepository<PlayList, Long> {

	public PlayList findByName(String name);

	@Modifying
	@Transactional
	public void deleteByName(String name);

}
