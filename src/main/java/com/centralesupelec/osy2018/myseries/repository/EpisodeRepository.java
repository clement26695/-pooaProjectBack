package com.centralesupelec.osy2018.myseries.repository;

import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Episode;

import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findBySeasonId(long id);

    Optional<Episode> findByTmdbId(long id);
}
