package com.centralesupelec.osy2018.myseries.repository;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.User;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByLoginAndPassword(String login, String password);

}