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
import com.centralesupelec.osy2018.myseries.repository.UserRepository;

@Controller
@RequestMapping(path="/api")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@PostMapping(path="/users")
	public @ResponseBody String addNewUser (@RequestParam String login, @RequestParam String lastName
			, @RequestParam String firstName,@RequestParam String birthDate, @RequestParam String email, @RequestParam String password, @RequestParam String description) {
		
		User n = new User();
		n.setLogin(login);
		n.setLastName(lastName);
		n.setFirstName(firstName);
		LocalDate date;
		date = LocalDate.parse(birthDate);
		n.setBirthdate(date);
		n.setPassword(password);
		n.setEmail(email);
		n.setDescription(description);
		n.setDateCreation(ZonedDateTime.now());
		userRepository.save(n);
		return "Saved";
	}

	@GetMapping(path="/users")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
}
