package com.centralesupelec.osy2018.myseries.controller;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.centralesupelec.osy2018.myseries.models.Authority;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.models.Watchlist;
import com.centralesupelec.osy2018.myseries.models.dto.ManagedUserVM;
import com.centralesupelec.osy2018.myseries.repository.AuthorityRepository;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.repository.WatchlistRepository;
import com.centralesupelec.osy2018.myseries.security.AuthoritiesConstants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(path="/api")
public class UserController {

	private UserRepository userRepository;

	private WatchlistRepository watchlistRepository;

	private AuthorityRepository authorityRepository;

	private final PasswordEncoder passwordEncoder;

	public UserController(PasswordEncoder passwordEncoder, UserRepository userRepository, WatchlistRepository watchlistRepository, AuthorityRepository authorityRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.watchlistRepository = watchlistRepository;
		this.authorityRepository = authorityRepository;
	}
	@PostMapping(path="/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void addNewUser (@Valid @RequestBody ManagedUserVM managedUserVM) {
		User newUser = new User();
		newUser.setLogin(managedUserVM.getLogin());
		newUser.setLastName(managedUserVM.getLastName());
		newUser.setFirstName(managedUserVM.getFirstName());
		newUser.setBirthdate(managedUserVM.getBirthdate());
		newUser.setPassword(passwordEncoder.encode(managedUserVM.getPassword()));
		newUser.setEmail(managedUserVM.getEmail());
		newUser.setDescription(managedUserVM.getDescription());
		newUser.setDateCreation(ZonedDateTime.now());

		Set<Authority> authorities = new HashSet<>();
		authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
		newUser.setAuthorities(authorities);
		
		userRepository.save(newUser);

		Watchlist watchlist = new Watchlist();
		watchlist.setUser(newUser);
		watchlistRepository.save(watchlist);
	}

	@GetMapping(path="/users")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@PostMapping(path="/login")
	public ResponseEntity<User> login(@RequestBody @Valid ManagedUserVM userDTO) {
		Optional<User> user = this.userRepository.findByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword());
		if (user.isPresent()) {
			return ResponseEntity.ok().body(user.get());
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
}
