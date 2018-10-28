package com.centralesupelec.osy2018.myseries.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.centralesupelec.osy2018.myseries.models.Genre;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface GenreRepository extends CrudRepository<Genre, Long> {
	
	@Query(value = "SELECT genre.* FROM genre "
			+"INNER JOIN user_genre ON genre.id = user_genre.genre_id "
			+"WHERE user_genre.user_id = ?1", nativeQuery = true)
	List<Genre> findByUserId(Long id);
	 
}