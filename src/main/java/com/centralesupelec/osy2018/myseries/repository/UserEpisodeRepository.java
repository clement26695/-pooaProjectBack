package com.centralesupelec.osy2018.myseries.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserEpisodeRepository extends CrudRepository<UserEpisode, Long> {
	
	@Query(value = "SELECT COUNT(episodeId) FROM user_episode "
            + "WHERE user_id = ?1 AND seen = True", nativeQuery = true)
    int countEpisodesSeenByUser(@Param("userId") Long userId);
}