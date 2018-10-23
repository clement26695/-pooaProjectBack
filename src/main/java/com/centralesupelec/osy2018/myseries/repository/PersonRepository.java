package com.centralesupelec.osy2018.myseries.repository;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Person;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findOneByFirstNameAndLastName(String firstName, String LastName);

}
