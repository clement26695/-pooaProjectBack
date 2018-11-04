package com.centralesupelec.osy2018.myseries.controller;

import java.util.Optional;
import java.util.Set;

import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.Watchlist;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.repository.WatchlistRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/api/watchlist")
public class WatchlistController {

    Logger logger = LoggerFactory.getLogger(WatchlistController.class);

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private SerieRepository serieRepository;

    /**
     * GET /watchlist/series/userId/:userId : get all series in the "userId" user's
     * watchlist.
     *
     * @param userId the "userId" of the user from who we want to retrieve the
     *               watchlist
     * @return the list of series followed by the user
     */
    @GetMapping(path = "/series/userId/{userId}")
    public @ResponseBody Set<Serie> watchlistSeries(@PathVariable("userId") long userId) {
        logger.info("GET request to list series in the watchlist of user with id {}", userId);

        Optional<Watchlist> watchlist = watchlistRepository.findOneByUserId(userId);

        if (watchlist.isPresent()) {
            return watchlist.get().getSeries();
        }

        return null;
    }

    /**
     * GET /watchlist/add/userId/:userId/serieId/:serieId : add the serie "serieId"
     * in the "userId" user's watchlist.
     *
     * @param userId  the "userId" of the user
     * @param serieId the "serieId" of the serie we want to add in the watchlist
     * @return the updated user's watchlist
     */
    @GetMapping(value = "/add/userId/{userId}/serieId/{serieId}")
    public ResponseEntity<Watchlist> addSerieToWatchlist(@PathVariable("serieId") long serieId,
            @PathVariable("userId") long userId) {

        logger.info("GET request to add serie {} in user {} watchlist", serieId, userId);

        Optional<Watchlist> watchlistResponse = watchlistRepository.findOneByUserId(userId);
        Optional<Serie> serieResponse = serieRepository.findById(serieId);

        if (watchlistResponse.isPresent() && serieResponse.isPresent()) {
            Watchlist watchlist = watchlistResponse.get();
            Serie serie = serieResponse.get();

            watchlist.getSeries().add(serie);

            watchlistRepository.save(watchlist);

            return ResponseEntity.ok().body(watchlist);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * GET /watchlist/remove/userId/:userId/serieId/:serieId : remove the serie "serieId"
     * in the "userId" user's watchlist.
     *
     * @param userId  the "userId" of the user
     * @param serieId the "serieId" of the serie we want to remove in the watchlist
     * @return the updated user's watchlist
     */
    @GetMapping(value = "/remove/userId/{userId}/serieId/{serieId}")
    public ResponseEntity<Watchlist> removeSerieFromWatchlist(@PathVariable("serieId") long serieId,
            @PathVariable("userId") long userId) {

        logger.info("GET request to remove serie {} in user {} watchlist", serieId, userId);

        Optional<Watchlist> watchlistResponse = watchlistRepository.findOneByUserId(userId);
        Optional<Serie> serieResponse = serieRepository.findById(serieId);

        if (watchlistResponse.isPresent() && serieResponse.isPresent()) {
            Watchlist watchlist = watchlistResponse.get();
            Serie serie = serieResponse.get();

            watchlist.getSeries().remove(serie);

            watchlistRepository.save(watchlist);

            return ResponseEntity.ok().body(watchlist);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
