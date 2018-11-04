package com.centralesupelec.osy2018.myseries.controller;

import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Season;
import com.centralesupelec.osy2018.myseries.repository.SeasonRepository;
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
@RequestMapping(path="/api/season")
public class SeasonController {

	Logger logger = LoggerFactory.getLogger(SeasonController.class);

	@Autowired
	private SeasonRepository seasonRepository;

    /**
     * GET /season/id/:id : get the "id" season.
     *
     * @param id the id of the season to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the season, or
     *         with status 404 (Not Found)
     */
	@GetMapping(value = "/id/{id}")
	@ResponseBody
	public ResponseEntity<Season> getSeasonById(@PathVariable("id") long id) {
        logger.info("GET request: get season with id {}", id);

        Optional<Season> season = seasonRepository.findById(id);

        return ResponseUtil.wrapOrNotFound(season);
    }

    /**
     * GET /season/serieId/:id : get all seasons with serie id "id".
     *
     * @param id the serie "id" of the seasons we want to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seasons in
     *         content
     */
    @GetMapping(value = "/serieId/{id}")
	@ResponseBody
	public List<Season> getSeasonsBySerieId(@PathVariable("id") long id) {
        logger.info("GET request: get all season from serie with id {}", id);

        return seasonRepository.findBySerieId(id);
    }


}
