package com.centralesupelec.osy2018.myseries.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.dto.StatisticsDTO;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.repository.UserEpisodeRepository;
import com.centralesupelec.osy2018.myseries.repository.WatchlistRepository;
import com.centralesupelec.osy2018.myseries.utils.factory.StatisticsDTOFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/statistics")
public class StatisticsController {

    Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private UserEpisodeRepository userEpisodeRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private SerieRepository serieRepository;

    /**
     * GET /statistics/all/:userId : get all statistics for the "userId" user.
     *
     * @param userId the "id" of the user from who we want to retrieve all statistics
     *
     * @return the ResponseEntity with status 200 (OK) and with body the page with
     *         statistics in content
     */
    @GetMapping(value = "/all/{userId}")
    public ResponseEntity<StatisticsDTO> getAllStats(@PathVariable("userId") Long userId) {
        logger.info("GET request : Get all statistics for user with id {}", userId);

        int episodeSeenCount = this.userEpisodeRepository.countEpisodesSeenByUser(userId);
        int serieInWatchlistCount = this.watchlistRepository.countSeriesInWatchlist(userId);

        List<Map<Genre, Integer>> serieByGenreCount = this.serieRepository.countSerieByGenre(userId);

        List<BigInteger> timeBySerie = this.userEpisodeRepository.getTimeBySerie(userId);
        Optional<BigInteger> totalTime = timeBySerie.stream().reduce((x, y) -> x.add(y));

        StatisticsDTO statisticsDTO = StatisticsDTOFactory.createStatisticsDTO(episodeSeenCount, serieInWatchlistCount,
                serieByGenreCount, totalTime);

        return ResponseEntity.ok().body(statisticsDTO);
    }

    /**
     * GET /statistics/episodeSeenCount/:userId : get the count of episode seen by a
     * the "userId" user.
     *
     * @param userId the "id" of the user from who we want to retrieve the count of
     *               episode seen
     * @return the ResponseEntity with status 200 (OK) and with body the count of
     *         episodes seen by the user with id "userId"
     */
    @GetMapping(value = "/episodeSeenCount/{userId}")
    public ResponseEntity<Integer> getEpisodeSeenCount(@PathVariable("userId") Long userId) {
        logger.info("GET request : Get statistic episodeSeenCount for user with id {}", userId);

        int count = this.userEpisodeRepository.countEpisodesSeenByUser(userId);

        return ResponseEntity.ok().body(count);
    }

    /**
     * GET /statistics/serieWatchlistCount/:userId : get the count of serie added in
     * the "userId" user's watchlist
     *
     * @param userId the "id" of the user from who we want to retrieve the count of
     *               serie in watchlist
     * @return the ResponseEntity with status 200 (OK) and with body the count of
     *         serie in watchlist for the user with id "userId"
     */
    @GetMapping(value = "/serieWatchlistCount/{userId}")
    public ResponseEntity<Integer> getSerieWatchlistCount(@PathVariable("userId") Long userId) {
        logger.info("GET request : Get statistic serieWatchlistCount for user with id {}", userId);

        int count = this.watchlistRepository.countSeriesInWatchlist(userId);

        return ResponseEntity.ok().body(count);
    }

    /**
     * GET /statistics/serieByGenreCount/:userId : get the count of serie followed
     * by a user grouped by genre
     *
     * @param userId the "id" of the user from who we want to retrieve the count of
     *               serie followed by a user grouped by genre serie in watchlist
     * @return the ResponseEntity with status 200 (OK) and with body the count of
     *         serie followed by a user grouped by genre
     */
    @GetMapping(value = "/serieByGenreCount/{userId}")
    public ResponseEntity<List<Map<Genre, Integer>>> getSerieByGenreCount(@PathVariable("userId") Long userId) {
        logger.info("GET request : Get statistic serieByGenreCount for user with id {}", userId);

        List<Map<Genre, Integer>> count = this.serieRepository.countSerieByGenre(userId);

        return ResponseEntity.ok().body(count);
    }

    /**
     * GET /statistics/getWatchTime/:userId : get the total amount of time spend
     * watching tv for a user in minutes
     *
     * @param userId the "id" of the user from who we want to retrieve the amount of
     *               time spent watching tv
     *
     * @return the ResponseEntity with status 200 (OK) and with body the amount of
     *         time spent watching tv or null if no episode was watched
     */
    @GetMapping(value = "/getWatchTime/{userId}")
    public ResponseEntity<Optional<BigInteger>> getWatchTime(@PathVariable("userId") Long userId) {
        logger.info("GET request : Get statistic getWatchTime for user with id {}", userId);

        List<BigInteger> timeBySerie = this.userEpisodeRepository.getTimeBySerie(userId);

        Optional<BigInteger> totalTime = timeBySerie.stream().reduce((x, y) -> x.add(y));
        return ResponseEntity.ok(totalTime);
    }
}
