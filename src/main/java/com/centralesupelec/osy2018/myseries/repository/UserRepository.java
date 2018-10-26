package com.centralesupelec.osy2018.myseries.repository;

import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByLoginAndPassword(String login, String password);

    @Query(value = "SELECT user.* FROM user "
            + "INNER JOIN watchlist ON watchlist.user_id = user.id "
            + "INNER JOIN serie_watchlist ON watchlist.id = serie_watchlist.watchlist_id "
            + "INNER JOIN serie ON serie.id = serie_watchlist.serie_id "
            + "WHERE serie.id = ?1", nativeQuery = true)
    List<User> findUserWithSerieInWatchlist(@Param("serieId") Long serieId);
}
