package com.centralesupelec.osy2018.myseries.controller;

import java.util.Optional;
import java.util.Set;

import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.Watchlist;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.repository.WatchlistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/api/watchlist")
public class WatchlistController {

  @Autowired
  private WatchlistRepository watchlistRepository;

  @Autowired
  private SerieRepository serieRepository;

  @GetMapping(path = "/series/userId/{userId}")
  public @ResponseBody Set<Serie> watchlistSeries(@PathVariable("userId") long userId) {
    Optional<Watchlist> watchlist = watchlistRepository.findOneByUserId(userId);

    if (watchlist.isPresent()) {
      return watchlist.get().getSeries();
    }

    return null;
  }

  @GetMapping(value = "/add/userId/{userId}/serieId/{serieId}")
  public @ResponseBody String addSerieToWatchlist(@PathVariable("serieId") long serieId, @PathVariable("userId") long userId) {
    Optional<Watchlist> watchlistResponse = watchlistRepository.findOneByUserId(userId);
    Optional<Serie> serieResponse = serieRepository.findById(serieId);

    if (watchlistResponse.isPresent() && serieResponse.isPresent()) {
      Watchlist watchlist = watchlistResponse.get();
      Serie serie = serieResponse.get();

      watchlist.getSeries().add(serie);

      watchlistRepository.save(watchlist);

      return "Add";
    }

    return null;
  }

}
