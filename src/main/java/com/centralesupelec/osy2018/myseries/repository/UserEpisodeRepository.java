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

    @Query(value = "SELECT AVG(rate) FROM user_episode "
    + "WHERE episode_id = :episodeId", nativeQuery = true)
    Float getAverageRateForEpisodeId(@Param("episodeId") Long episodeId);

    @Query(value = "SELECT user_episode.rate, user_episode.seen, episode.id FROM "
    + "(SELECT episode.id, episode.season_id FROM episode "
    + "INNER JOIN season ON episode.season_id = season.id "
    + "WHERE season.serie_id = :serieId) AS episode "
    + "LEFT OUTER JOIN "
    + "(SELECT rate,seen, episode_id FROM user_episode "
    + "WHERE user_id = :userId) AS user_episode "
    + "ON user_episode.episode_id = episode.id ", nativeQuery = true)
	List<Object[]> getEpisodeUserAndEpisodeBySerieIdAndUserId(@Param("serieId") Long serieId,
            @Param("userId") Long userId);

    @Query(value = "SELECT episode_id, AVG(rate) FROM user_episode "
    + "INNER JOIN episode ON episode.id = user_episode.episode_id "
    + "INNER JOIN season ON episode.season_id = season.id "
    + "WHERE season.serie_id = :serieId "
    + "GROUP BY episode_id", nativeQuery = true)
    List<Object[]> getAverageRateByEpisodeOfSerieId(@Param("serieId") Long serieId);

}
