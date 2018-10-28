package com.centralesupelec.osy2018.myseries.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.dto.PreferenceDTO;
import com.centralesupelec.osy2018.myseries.repository.GenreRepository;
import com.centralesupelec.osy2018.myseries.utils.GenreUtils;
import com.centralesupelec.osy2018.myseries.utils.RecommendationUtils;
import com.centralesupelec.osy2018.myseries.utils.factory.PreferenceDTOFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


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
