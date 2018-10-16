package com.centralesupelec.osy2018.myseries.controller;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.models.Watchlist;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.repository.WatchlistRepository;

@Controller
@RequestMapping(path="/api")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WatchlistRepository watchlistRepository;

	@PostMapping(path="/users")
	public @ResponseBody String addNewUser (@RequestParam String login, @RequestParam String lastName
			, @RequestParam String firstName,@RequestParam String birthDate, @RequestParam String email, @RequestParam String password, @RequestParam String description) {
		
		User user = new User();
		user.setLogin(login);
		user.setLastName(lastName);
		user.setFirstName(firstName);
		LocalDate date;
		date = LocalDate.parse(birthDate);
		user.setBirthdate(date);
		user.setPassword(password);
		user.setEmail(email);
		user.setDescription(description);
		user.setDateCreation(ZonedDateTime.now());
		
		userRepository.save(user);

		Watchlist watchlist = new Watchlist();
		watchlist.setUser(user);
		watchlistRepository.save(watchlist);
		
		return "Saved";
	}

	@GetMapping(path="/users")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
}
