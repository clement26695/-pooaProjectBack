package com.centralesupelec.osy2018.myseries.repository;

import com.centralesupelec.osy2018.myseries.models.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    @Query(value = "SELECT genre.* FROM genre "
            + "INNER JOIN user_genre ON genre.id = user_genre.genre_id "
            + "WHERE user_genre.user_id = ?1", nativeQuery = true)
    List<Genre> findByUserId(Long id);

}