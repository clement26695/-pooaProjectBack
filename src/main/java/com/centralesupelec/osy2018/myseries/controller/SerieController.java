package com.centralesupelec.osy2018.myseries.controller;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.dto.PreferenceDTO;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.utils.GenreUtils;
import com.centralesupelec.osy2018.myseries.utils.RecommendationUtils;
import com.centralesupelec.osy2018.myseries.utils.api_importer.MovieDBImporter;
import com.centralesupelec.osy2018.myseries.utils.factory.PreferenceDTOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

@Controller
@RequestMapping(path="/api/serie")
public class SerieController {

    Logger logger = LoggerFactory.getLogger(SerieController.class);

    private MovieDBImporter movieDBImporter;

    private SerieRepository serieRepository;

    private GenreUtils genreUtils;

    private RecommendationUtils recommendationUtils;

    public SerieController(MovieDBImporter movieDBImporter, SerieRepository serieRepository, GenreUtils genreUtils, RecommendationUtils recommendationUtils) {
        this.movieDBImporter = movieDBImporter;
        this.serieRepository = serieRepository;
        this.genreUtils = genreUtils;
        this.recommendationUtils = recommendationUtils;
    }

    /**
     * GET /serie/refresh : import all database.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping(path="/refresh")
    public ResponseEntity<String> refresh() {
        logger.info("GET request : Refresh database");

        this.movieDBImporter.importDataFromTMDBApi();

        return ResponseEntity.ok("Saved");
    }

    /**
     * GET /serie/all : get all series.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body the page with
     *         series in content
     */
    @GetMapping(path="/all")
    public ResponseEntity<Page<Serie>> seriesAll(Pageable pageable) {
        logger.info("GET request : Get all series with {} results per page", pageable.getPageSize());

        final Page<Serie> page = this.serieRepository.findAll(pageable);

        return ResponseEntity.ok().body(page);
    }

    /**
     * GET /serie/id/:id : get the "id" serie.
     *
     * @param id the id of the serie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serie, or
     *         with status 404 (Not Found)
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Serie> getSerieById(@PathVariable("id") long id) {
        logger.info("GET request : Get serie with id {}", id);
        return serieRepository.findById(id);
    }

    /**
     * GET /serie/name?name=:name : get the serie with name containing "name".
     *
     * @param name the name of the serie to retrieve
     * @return the list of series with name containing "name"
     */
    @GetMapping(value = "/name")
    @ResponseBody
    public List<Serie> getSerieByName(@RequestParam(value = "name", required = false) String name) {
        logger.info("GET request : Get all series with name {}", name);
        return serieRepository.findByNameContaining(name);
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
        logger.info("POST request : Get Series ordered by preference");

        Map<Genre, Double> map = this.genreUtils.getMapGenreDouble(genreScores);

        List<Entry<Serie, Double>> entries = this.recommendationUtils.computeRecommendation(map);

        return PreferenceDTOFactory.createListPreferenceDTO(entries);

    }

}
