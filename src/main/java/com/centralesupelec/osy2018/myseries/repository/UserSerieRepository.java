package com.centralesupelec.osy2018.myseries.repository;

import com.centralesupelec.osy2018.myseries.models.UserSerie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserSerieRepository extends CrudRepository<UserSerie, Long> {

    Optional<UserSerie> findByUserIdAndSerieId(long userId, long serieId);

    @Query(value = "SELECT AVG(rate) FROM user_serie "
            + "WHERE serie_id = :serieId", nativeQuery = true)
    Float getAverageRateForSerieId(Long serieId);
}
