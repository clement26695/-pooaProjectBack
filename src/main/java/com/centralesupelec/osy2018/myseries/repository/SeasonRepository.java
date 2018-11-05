package com.centralesupelec.osy2018.myseries.repository;

import com.centralesupelec.osy2018.myseries.models.Season;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SeasonRepository extends CrudRepository<Season, Long> {

    List<Season> findBySerieId(long id);

    Optional<Season> findByTmdbId(long id);
}
