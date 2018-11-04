package com.centralesupelec.osy2018.myseries.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.models.dto.ManagedUserVM;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.utils.ResponseUtil;
import com.centralesupelec.osy2018.myseries.utils.exceptions.EmailAlreadyUsedException;
import com.centralesupelec.osy2018.myseries.utils.exceptions.LoginAlreadyUsedException;
import com.centralesupelec.osy2018.myseries.utils.factory.UserFactory;
import com.centralesupelec.osy2018.myseries.utils.factory.WatchlistFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/api")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFactory userFactory;

    @Autowired
	private WatchlistFactory watchlistFactory;

    /**
     * POST /register : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used
     *
     * @param managedUserVM the user to create
     * @return
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         user, or with status 400 (Bad Request) if the login or email is
     *         already in use
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is
     *                                  already in use
     */
	@PostMapping(path="/register")
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody ManagedUserVM managedUserVM) {
        logger.info("POST request to save User : {}", managedUserVM.getLogin());

        try {
            User newUser = this.userFactory.createAndSaveUser(managedUserVM);
            this.watchlistFactory.createAndSaveWatchlist(newUser);
        } catch (LoginAlreadyUsedException | EmailAlreadyUsedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
		}

        return new ResponseEntity<>(HttpStatus.CREATED);
	}

    /**
     * GET /users : get all users.
     *
     * @return the list of users
     */
	@GetMapping(path="/users")
	public @ResponseBody Iterable<User> getAllUsers() {
        logger.info("GET request to list all users");

        return userRepository.findAll();
	}

    /**
     * POST /login : authenticate a user.
     *
     * @param managedUserVM a user with login and password
     * @return the ResponseEntity with status 200 (OK) and with body the user, or
     *         with status 404 (Not Found)
     */
	@PostMapping(path="/login")
	public ResponseEntity<User> login(@RequestBody @Valid ManagedUserVM managedUserVM) {
        logger.info("POST request to login");

        Optional<User> user = this.userRepository.findByLoginAndPassword(managedUserVM.getLogin(),
                managedUserVM.getPassword());

		return ResponseUtil.wrapOrNotFound(user);

	}

}
