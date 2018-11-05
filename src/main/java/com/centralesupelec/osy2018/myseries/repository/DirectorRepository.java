package com.centralesupelec.osy2018.myseries.repository;

import com.centralesupelec.osy2018.myseries.models.Director;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DirectorRepository extends CrudRepository<Director, Long> {

    Optional<Director> findOneByFirstNameAndLastName(String firstName, String LastName);

    Optional<Director> findByTmdbId(long id);
}
