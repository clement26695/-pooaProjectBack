package com.centralesupelec.osy2018.myseries.repository;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Director;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DirectorRepository extends CrudRepository<Director, Long> {

    Optional<Director> findOneByFirstNameAndLastName(String firstName, String LastName);

    @Query(value = "INSERT INTO Actor VALUES (?1)", nativeQuery = true)
    void addDirectorIfAlreadyInPerson(@Param("id") Long id);
}
