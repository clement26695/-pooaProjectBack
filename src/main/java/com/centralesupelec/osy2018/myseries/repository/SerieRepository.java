package com.centralesupelec.osy2018.myseries.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Serie;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SerieRepository extends CrudRepository<Serie, Long> {

    List<Serie> findByName(String name);

    @Query(value = "SELECT serie.* FROM serie " +
    "INNER JOIN season ON serie.id = season.serie_id " +
    "INNER JOIN episode ON season.id = episode.season_id " +
    "WHERE episode.air_date BETWEEN NOW() AND NOW() + INTERVAL 1 DAY " +
    "GROUP BY id", nativeQuery = true)
    List<Serie> findSerieWithEpisodeTomorrow();

	Optional<Serie> findByTmdbId(long id);
}
