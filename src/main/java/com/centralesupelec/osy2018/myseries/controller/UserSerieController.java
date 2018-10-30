package com.centralesupelec.osy2018.myseries.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.models.UserSerie;
import com.centralesupelec.osy2018.myseries.models.Watchlist;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.repository.UserSerieRepository;
import com.centralesupelec.osy2018.myseries.repository.WatchlistRepository;

@Controller
@RequestMapping(path = "/api/userserie")
public class UserSerieController {

	@Autowired
	private UserSerieRepository userSerieRepository;
	
	
	@GetMapping(value = "/rate/userId/{userId}/serieId/{serieId}")
	public int getRate(@PathVariable("userId") long userId, @PathVariable("serieId") long serieId) {
		Optional<UserSerie> userSerieResponse = userSerieRepository.findByUserIdAndSerieId(userId,serieId);
		if (userSerieResponse.isPresent()) {
			return userSerieResponse.get().getRate();
		}
		
		else {
			return -1;
		}	
	}
	
	@GetMapping(value = "/addRate/userId/{userId}/serieId/{serieId}/rate/{rate}")
	public void addRateToSerie(@PathVariable("userId") long userId, @PathVariable("serieId") long serieId, int rate) {
	    Optional<UserSerie> userSerieResponse = userSerieRepository.findByUserIdAndSerieId(userId,serieId);
	    
	    if (userSerieResponse.isPresent()) {
	    	UserSerie userSerie = userSerieResponse.get();
	    	
	    	userSerie.setRate(rate);
	    	
	    	userSerieRepository.save(userSerie);
	    }
	    else {
	    	UserSerie userSerie = new UserSerie();
	    	userSerie.setRate(rate);
	    	
	    	userSerieRepository.save(userSerie);
	    	
	    }
	    
		
	}
}
