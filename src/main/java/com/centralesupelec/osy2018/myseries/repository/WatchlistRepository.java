package com.centralesupelec.osy2018.myseries.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import com.centralesupelec.osy2018.myseries.models.Watchlist;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface WatchlistRepository extends CrudRepository<Watchlist, Long> {

  Optional<Watchlist> findOneByUserId(Long userId);

}