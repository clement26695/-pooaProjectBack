package com.centralesupelec.osy2018.myseries.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.UserEpisode;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserEpisodeRepository extends CrudRepository<UserEpisode, Long> {

    @Query(value = "SELECT * FROM user_episode WHERE user_id = :userId AND episode_id = :episodeId", nativeQuery = true)
    Optional<UserEpisode> findOneByUserIdAndEpisodeId(@Param("userId") long userId, @Param("episodeId") long episodeId);

    @Query(value = "SELECT COUNT(episode_id) FROM user_episode "
            + "WHERE user_id = ?1 AND seen = True", nativeQuery = true)
    int countEpisodesSeenByUser(@Param("userId") Long userId);

    @Query(value = "SELECT COUNT(user_episode.episode_id) * serie.episode_run_time FROM user_episode "
        + "INNER JOIN episode ON episode.id = user_episode.episode_id "
        + "INNER JOIN season ON season.id = episode.season_id "
        + "INNER JOIN serie ON serie.id = season.serie_id "
        + "WHERE user_episode.user_id = ?1 AND user_episode.seen = True "
        + "GROUP BY serie.id", nativeQuery = true)
    List<BigInteger> getTimeBySerie(@Param("userId") Long userId);
}
