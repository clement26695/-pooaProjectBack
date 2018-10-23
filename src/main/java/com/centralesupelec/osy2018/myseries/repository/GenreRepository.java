package com.centralesupelec.osy2018.myseries.repository;

import org.springframework.data.repository.CrudRepository;

import com.centralesupelec.osy2018.myseries.models.Genre;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GenreRepository extends CrudRepository<Genre, Long> {

}