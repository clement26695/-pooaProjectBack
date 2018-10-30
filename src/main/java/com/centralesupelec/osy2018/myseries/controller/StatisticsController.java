package com.centralesupelec.osy2018.myseries.controller;

import com.centralesupelec.osy2018.myseries.models.dto.StatisticsDTO;
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

    @GetMapping(value = "/all/{userId}")
    public ResponseEntity<StatisticsDTO> getAllStats(@PathVariable("userId") Long userId) {
        int episodeSeenCount = this.userEpisodeRepository.countEpisodesSeenByUser(userId);
        int serieInWatchlistCount = this.watchlistRepository.countSeriesInWatchlist(userId);

        StatisticsDTO statisticsDTO = StatisticsDTOFactory.createStatisticsDTO(episodeSeenCount, serieInWatchlistCount);

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
}
