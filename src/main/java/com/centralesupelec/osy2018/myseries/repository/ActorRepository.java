package com.centralesupelec.osy2018.myseries.repository;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Actor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ActorRepository extends CrudRepository<Actor, Long> {

    Optional<Actor> findOneByFirstNameAndLastName(String firstName, String LastName);

    @Modifying
    @Query(value = "INSERT INTO Actor VALUES (?1)", nativeQuery = true)
    void addActorIfAlreadyInPerson(@Param("id") Long id);
}
