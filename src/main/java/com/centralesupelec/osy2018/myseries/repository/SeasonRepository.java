package com.centralesupelec.osy2018.myseries.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.centralesupelec.osy2018.myseries.models.Season;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SeasonRepository extends CrudRepository<Season, Long> {

    List<Season> findBySerieId(long id);
}