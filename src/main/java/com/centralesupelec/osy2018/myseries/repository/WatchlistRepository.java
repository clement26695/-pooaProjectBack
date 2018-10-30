package com.centralesupelec.osy2018.myseries.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import com.centralesupelec.osy2018.myseries.models.Watchlist;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface WatchlistRepository extends CrudRepository<Watchlist, Long> {

  Optional<Watchlist> findOneByUserId(Long userId);
  
  @Query(value = "SELECT COUNT(serie_id) FROM serie_watchlist "
		  + "INNER JOIN watchlist ON watchlist.id = serie_watchlist.watchlist_id"
          + "WHERE watchlist.user_id = ?1", nativeQuery = true)
  int countSeriesInWatchlist(@Param("userId") Long userId);

}