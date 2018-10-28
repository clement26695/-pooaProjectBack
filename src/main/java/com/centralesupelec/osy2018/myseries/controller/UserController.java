package com.centralesupelec.osy2018.myseries.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.models.dto.ManagedUserVM;
import com.centralesupelec.osy2018.myseries.repository.GenreRepository;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.utils.factory.UserFactory;
import com.centralesupelec.osy2018.myseries.utils.factory.WatchlistFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(path="/api")
public class UserController {
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFactory userFactory;

    @Autowired
	private WatchlistFactory watchlistFactory;

    @Autowired
	private GenreRepository genreRepository;

	@PostMapping(path="/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void addNewUser (@Valid @RequestBody ManagedUserVM managedUserVM) {
        User newUser = this.userFactory.createAndSaveUser(managedUserVM);

        this.watchlistFactory.createAndSaveWatchlist(newUser);

	}

	@GetMapping(path="/users")
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

	@RequestMapping(value = "/user/id/{id}/genres", method = RequestMethod.GET)
	@ResponseBody
	public List<Genre> getGenreByUserId(@PathVariable("id") Long id) {
   		return genreRepository.findByUserId(id);
	}


}
