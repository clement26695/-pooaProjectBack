package com.centralesupelec.osy2018.myseries.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.centralesupelec.osy2018.myseries.models.UserSerie;

public interface UserSerieRepository extends CrudRepository<UserSerie, Long> {
	Optional<UserSerie> findByUserIdAndSerieId(long userId, long serieId);
}
