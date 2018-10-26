package com.centralesupelec.osy2018.myseries.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.centralesupelec.osy2018.myseries.models.Serie;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SerieRepository extends CrudRepository<Serie, Long> {

    List<Serie> findByName(String name);
    
    @Query(value = "SELECT serie.* FROM serie "
			+"INNER JOIN serie_genre ON serie.id = serie_genre.serie_id "
			+"INNER JOIN genre ON genre.id = serie_genre.genre_id "
			+"WHERE genre.name = ?1", nativeQuery = true)
    List<Serie> findByGenreName(String genre);
}