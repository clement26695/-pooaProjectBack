package com.centralesupelec.osy2018.myseries.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.models.UserSerie;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.repository.UserSerieRepository;

@Controller
@RequestMapping(path = "/api/userserie")
public class UserSerieController {

	@Autowired
	private UserSerieRepository userSerieRepository;
	@Autowired
	private SerieRepository serieRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/rate/userId/{userId}/serieId/{serieId}")
	@ResponseBody
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
	@ResponseBody
	public void addRateToSerie(@PathVariable("userId") long userId, @PathVariable("serieId") long serieId,@PathVariable("rate") int rate) {
	    Optional<UserSerie> userSerieResponse = userSerieRepository.findByUserIdAndSerieId(userId,serieId);
	    Optional<Serie> serieResponse = serieRepository.findById(serieId);
	    Optional<User> userResponse = userRepository.findById(userId);
	    
	    if (userSerieResponse.isPresent()) {
	    	UserSerie userSerie = userSerieResponse.get();
	    	
	    	userSerie.setRate(rate);
	    	
	    	userSerieRepository.save(userSerie);
	    }
	    else if (serieResponse.isPresent() && userResponse.isPresent()) {
	    	UserSerie userSerie = new UserSerie();
	    	Serie serie = serieResponse.get();
	    	User user = userResponse.get();
	    	
	    	userSerie.setSerie(serie);
	    	userSerie.setUser(user);
	    	userSerie.setRate(rate);
	    	
	    	userSerieRepository.save(userSerie);
	    	
	    }
	    
		
	}
}
