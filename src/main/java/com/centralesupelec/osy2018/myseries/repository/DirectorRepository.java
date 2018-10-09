package com.centralesupelec.osy2018.myseries.repository;

import org.springframework.data.repository.CrudRepository;

import com.centralesupelec.osy2018.myseries.models.Director;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DirectorRepository extends CrudRepository<Director, Long> {

}