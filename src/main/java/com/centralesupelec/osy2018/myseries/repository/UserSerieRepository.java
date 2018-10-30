package com.centralesupelec.osy2018.myseries.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.centralesupelec.osy2018.myseries.models.UserSerie;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserSerieRepository extends CrudRepository<UserSerie, Long> {
	Optional<UserSerie> findByUserIdAndSerieId(long userId, long serieId);
}