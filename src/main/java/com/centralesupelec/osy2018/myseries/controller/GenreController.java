package com.centralesupelec.osy2018.myseries.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.GenreRepository;


@Controller
@RequestMapping(path="/api/genre")
public class GenreController {

	@Autowired
	private GenreRepository genreRepository;
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Genre> genresAll(){
		return genreRepository.findAll();
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Genre> getGenreById(@PathVariable("id") long id) {
   		return genreRepository.findById(id);
	}
}