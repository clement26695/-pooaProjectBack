package com.centralesupelec.osy2018.myseries.repository;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Actor;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ActorRepository extends CrudRepository<Actor, Long> {

    Optional<Actor> findOneByFirstNameAndLastName(String firstName, String LastName);

    Optional<Actor> findByTmdbId(long id);
}
