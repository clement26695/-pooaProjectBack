package com.centralesupelec.osy2018.myseries.repository;

import com.centralesupelec.osy2018.myseries.models.Actor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ActorRepository extends CrudRepository<Actor, Long> {

    Optional<Actor> findOneByFirstNameAndLastName(String firstName, String LastName);

    Optional<Actor> findByTmdbId(long id);
}
