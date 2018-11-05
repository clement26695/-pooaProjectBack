package com.centralesupelec.osy2018.myseries.repository;

import com.centralesupelec.osy2018.myseries.models.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findBySeasonId(long id);

    Optional<Episode> findByTmdbId(long id);
}
