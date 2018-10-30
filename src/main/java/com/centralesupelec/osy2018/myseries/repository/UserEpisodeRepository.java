package com.centralesupelec.osy2018.myseries.repository;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.UserEpisode;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserEpisodeRepository extends CrudRepository<UserEpisode, Long> {

    @Query(value = "SELECT * FROM user_episode WHERE user_id = :userId AND episode_id = :episodeId", nativeQuery = true)
    Optional<UserEpisode> findOneByUserIdAndEpisodeId(@Param("userId") long userId,
            @Param("episodeId") long episodeId);

}
