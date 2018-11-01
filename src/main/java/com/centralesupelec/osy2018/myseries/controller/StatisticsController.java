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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/api/statistics")
public class StatisticsController {

    @Autowired
    private UserEpisodeRepository userEpisodeRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private SerieRepository serieRepository;

    @GetMapping(value = "/all/{userId}")
    public ResponseEntity<StatisticsDTO> getAllStats(@PathVariable("userId") Long userId) {
        int episodeSeenCount = this.userEpisodeRepository.countEpisodesSeenByUser(userId);
        int serieInWatchlistCount = this.watchlistRepository.countSeriesInWatchlist(userId);

        List<Map<Genre, Integer>> serieByGenreCount = this.serieRepository.countSerieByGenre(userId);

        List<BigInteger> timeBySerie = this.userEpisodeRepository.getTimeBySerie(userId);
        Optional<BigInteger> totalTime = timeBySerie.stream().reduce((x, y) -> x.add(y));

        StatisticsDTO statisticsDTO = StatisticsDTOFactory.createStatisticsDTO(episodeSeenCount, serieInWatchlistCount,
                serieByGenreCount, totalTime);

        return ResponseEntity.ok().body(statisticsDTO);
    }

    @GetMapping(value = "/episodeSeenCount/{userId}")
    public ResponseEntity<Integer> getEpisodeSeenCount(@PathVariable("userId") Long userId) {
        int count = this.userEpisodeRepository.countEpisodesSeenByUser(userId);

        return ResponseEntity.ok().body(count);
    }

    @GetMapping(value = "/serieWatchlistCount/{userId}")
    public ResponseEntity<Integer> getSerieWatchlistCount(@PathVariable("userId") Long userId) {
        int count = this.watchlistRepository.countSeriesInWatchlist(userId);

        return ResponseEntity.ok().body(count);
    }

    @GetMapping(value = "/serieByGenreCount/{userId}")
    public ResponseEntity<List<Map<Genre, Integer>>> getSerieByGenreCount(@PathVariable("userId") Long userId) {
        List<Map<Genre,Integer>> count = this.serieRepository.countSerieByGenre(userId);
        return ResponseEntity.ok().body(count);
    }

    @GetMapping(value = "/getTimeBySerie/{userId}")
    public ResponseEntity<Optional<BigInteger>> getTimeBySerie(@PathVariable("userId") Long userId) {
        List<BigInteger> timeBySerie = this.userEpisodeRepository.getTimeBySerie(userId);

        Optional<BigInteger> totalTime = timeBySerie.stream().reduce((x,y) -> x.add(y));
        return ResponseEntity.ok().body(totalTime);
    }
}
