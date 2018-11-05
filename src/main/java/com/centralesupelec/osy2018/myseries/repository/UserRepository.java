package com.centralesupelec.osy2018.myseries.repository;

import com.centralesupelec.osy2018.myseries.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    @Query(value = "SELECT user.* FROM user "
            + "INNER JOIN watchlist ON watchlist.user_id = user.id "
            + "INNER JOIN serie_watchlist ON watchlist.id = serie_watchlist.watchlist_id "
            + "INNER JOIN serie ON serie.id = serie_watchlist.serie_id "
            + "WHERE serie.id = ?1", nativeQuery = true)
    List<User> findUserWithSerieInWatchlist(@Param("serieId") Long serieId);

}
