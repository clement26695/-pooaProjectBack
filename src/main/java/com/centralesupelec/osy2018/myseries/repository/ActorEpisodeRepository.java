package com.centralesupelec.osy2018.myseries.repository;

import com.centralesupelec.osy2018.myseries.models.ActorEpisode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActorEpisodeRepository extends CrudRepository<ActorEpisode, Long> {

    @Query(value = "INSERT INTO actor_episode (episode_id, actor_id, character_name) "
            + "(SELECT episode.id, :actorId, :characterName FROM episode "
            + "INNER JOIN season ON season.id = episode.season_id "
            + "INNER JOIN serie ON serie.id = season.serie_id "
            + "WHERE serie.id = :serieId)", nativeQuery = true)
    @Modifying
    void associateActorToEpisodes(@Param("serieId") Long serieId, @Param("actorId") Long actorId, @Param("characterName") String characterName);
}
