package com.centralesupelec.osy2018.myseries.controller;

import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.repository.GenreRepository;
import com.centralesupelec.osy2018.myseries.utils.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path="/api/genre")
public class GenreController {

	Logger logger = LoggerFactory.getLogger(GenreController.class);

	@Autowired
    private GenreRepository genreRepository;

    /**
     * GET /genre/all : get all genres.
     *
     * @return the list of all genres
     */
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Genre> genresAll(){
        logger.info("GET request: get all genres");

        return genreRepository.findAll();
	}

    /**
     * GET /genre/id/:id : get the "id" genre.
     *
     * @param id the id of the genre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genre, or
     *         with status 404 (Not Found)
     */
	@GetMapping(value = "/id/{id}")
	@ResponseBody
	public ResponseEntity<Genre> getGenreById(@PathVariable("id") long id) {
        logger.info("GET request: get genre with id {}", id);

        Optional<Genre> genre = genreRepository.findById(id);

   		return ResponseUtil.wrapOrNotFound(genre);
    }

    /**
     * GET /genre/userId/:id : get all genre associated with "id" user.
     *
     * @param id the "id" of the user
     * @return the list of genre associated with the user
     */
    @GetMapping(value = "/userId/{id}")
    @ResponseBody
    public List<Genre> getGenreByUserId(@PathVariable("id") Long id) {
        return genreRepository.findByUserId(id);
    }

}
