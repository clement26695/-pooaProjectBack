package com.centralesupelec.osy2018.myseries.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.dto.PreferenceDTO;
import com.centralesupelec.osy2018.myseries.repository.GenreRepository;
import com.centralesupelec.osy2018.myseries.utils.RecommendationUtils;

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

    @Autowired
    private RecommendationUtils recommendationUtils;

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Genre> genresAll(){
		return genreRepository.findAll();
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Genre> getGenreById(@PathVariable("id") long id) {
   		return genreRepository.findById(id);
    }

    /*
    @params: Map avec comme clé: Id du genre, valeur: score associé au genre
    */
    @PostMapping(value = "/testPreferences", produces = "application/json")
    @ResponseBody
    public List<PreferenceDTO> getPreferences(@RequestBody Map<Long, Double> genreScores) {
        Map<Genre, Double> map = new HashMap<>();

        for (Long id : genreScores.keySet()) {
            Optional<Genre> genre = this.genreRepository.findById(id);

            if (genre.isPresent()) {
                map.put(genre.get(), genreScores.get(id));
            }
        }

        List<Entry<Serie, Double>> entries = this.recommendationUtils.computeRecommendation(map);

        List<PreferenceDTO> preferences = new ArrayList<>();

        for (Entry<Serie, Double> entry : entries) {
            PreferenceDTO preferenceDTO = new PreferenceDTO();
            preferenceDTO.setSerie(entry.getKey());
            preferenceDTO.setScore(entry.getValue());

            preferences.add(preferenceDTO);
        }

        return preferences;

    }
}
