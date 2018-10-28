package com.centralesupelec.osy2018.myseries.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.dto.PreferenceDTO;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.utils.GenreUtils;
import com.centralesupelec.osy2018.myseries.utils.RecommendationUtils;
import com.centralesupelec.osy2018.myseries.utils.api_importer.MovieDBImporter;
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
@RequestMapping(path="/api/serie")
public class SerieController {

	private final MovieDBImporter movieDBImporter;

	@Autowired
    private SerieRepository serieRepository;

    @Autowired
    private GenreUtils genreUtils;

    @Autowired
    private RecommendationUtils recommendationUtils;

	public SerieController(MovieDBImporter movieDBImporter) {
		this.movieDBImporter = movieDBImporter;
	}


	@GetMapping(path="/refresh")
	public @ResponseBody String refresh() {
		this.movieDBImporter.importDataFromTMDBApi();
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Serie> seriesAll(){
		return serieRepository.findAll();
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Serie> getSerieById(@PathVariable("id") long id) {
   		return serieRepository.findById(id);
	}

	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	@ResponseBody
	public List<Serie> getSerieByName(@PathVariable("name") String name) {
   		return serieRepository.findByName(name);
    }

    /**
     * POST /serie/preferences : Get Series ordered by preference.
     *
     * @param genreScores a map with the Id of a Genre as a key and
     *        the ponderation score as a value
     * @return the list of preferences as {@link PreferenceDTO}
     */
    @PostMapping(value = "/preferences", produces = "application/json")
    @ResponseBody
    public List<PreferenceDTO> getPreferences(@RequestBody Map<Long, Double> genreScores) {
        Map<Genre, Double> map = this.genreUtils.getMapGenreDouble(genreScores);

        List<Entry<Serie, Double>> entries = this.recommendationUtils.computeRecommendation(map);

        List<PreferenceDTO> preferences = PreferenceDTOFactory.createListPreferenceDTO(entries);

        return preferences;

    }

}
