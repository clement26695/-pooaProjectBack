package com.centralesupelec.osy2018.myseries.utils.factory;

import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.models.Watchlist;
import com.centralesupelec.osy2018.myseries.repository.WatchlistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchlistFactory {

    WatchlistRepository watchlistRepository;

    public WatchlistFactory(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }

    public static Watchlist createWatchlist(User user) {
        Watchlist newWatchlist = new Watchlist();
        newWatchlist.setUser(user);

        return newWatchlist;
    }

    public Watchlist createAndSaveWatchlist(User user) {
        Watchlist newWatchlist = WatchlistFactory.createWatchlist(user);
        this.watchlistRepository.save(newWatchlist);

        return newWatchlist;
    }
}
