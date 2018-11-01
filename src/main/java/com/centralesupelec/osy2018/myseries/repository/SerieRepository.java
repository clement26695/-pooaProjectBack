package com.centralesupelec.osy2018.myseries.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.Serie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SerieRepository extends JpaRepository<Serie, Long> {

    List<Serie> findByName(String name);

    @Query(value = "SELECT serie.* FROM serie " +
    "INNER JOIN season ON serie.id = season.serie_id " +
    "INNER JOIN episode ON season.id = episode.season_id " +
    "WHERE episode.air_date BETWEEN NOW() AND NOW() + INTERVAL 1 DAY " +
    "GROUP BY id", nativeQuery = true)
    List<Serie> findSerieWithEpisodeTomorrow();

    Optional<Serie> findByTmdbId(long id);

    @Query(value = "SELECT genre.id,genre.name,count(serie.id) FROM serie "
    + "INNER JOIN serie_genre ON serie.id = serie_genre.serie_id "
    + "INNER JOIN genre ON serie_genre.genre_id = genre.id "
    + "INNER JOIN serie_watchlist ON serie.id = serie_watchlist.serie_id "
    + "INNER JOIN watchlist ON serie_watchlist.watchlist_id = watchlist.id "
    + "WHERE watchlist.user_id = :userId "
    + "GROUP BY serie_genre.genre_id", nativeQuery = true)
    List<Map<Genre, Integer>> countSerieByGenre(@Param("userId") Long userId);

    List<Serie> findByNameContaining(@Param("name") String name);
}
