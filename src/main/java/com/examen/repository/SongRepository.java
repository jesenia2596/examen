package com.examen.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.examen.entity.Song;

public interface SongRepository extends CrudRepository<Song, Long> {

	@Query( value = "select s.* from songs s join play_lists p where p.name = :name", nativeQuery = true)
	public List<Song> findByPlayListName( @Param("name") String name);
	
	@Query( value = "select s.* from songs s join play_lists p where p.name = :name and s.title = :title", nativeQuery = true)
	public Song findByPlayListName( @Param("name") String name, @Param("title") String title);
	
	@Transactional
	@Modifying
	@Query( value = "delete from songs where title = :title and play_list_id = ( select id from play_lists where name = :playList)", nativeQuery = true)
	public void deletePlayListNameAndSong( @Param("playList") String playList, @Param("title") String title);
}
