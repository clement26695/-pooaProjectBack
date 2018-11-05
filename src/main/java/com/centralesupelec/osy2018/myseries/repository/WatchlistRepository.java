package com.centralesupelec.osy2018.myseries.repository;

import com.centralesupelec.osy2018.myseries.models.Watchlist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WatchlistRepository extends CrudRepository<Watchlist, Long> {

    Optional<Watchlist> findOneByUserId(Long userId);

    @Query(value = "SELECT COUNT(serie_id) FROM serie_watchlist "
            + "INNER JOIN watchlist ON watchlist.id = serie_watchlist.watchlist_id "
            + "WHERE watchlist.user_id = :userId", nativeQuery = true)
    int countSeriesInWatchlist(@Param("userId") Long userId);

}
