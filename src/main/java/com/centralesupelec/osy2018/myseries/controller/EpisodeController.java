package com.centralesupelec.osy2018.myseries.controller;

import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Episode;
import com.centralesupelec.osy2018.myseries.repository.EpisodeRepository;
import com.centralesupelec.osy2018.myseries.utils.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/api/episode")
public class EpisodeController {

    Logger logger = LoggerFactory.getLogger(EpisodeController.class);

	@Autowired
	private EpisodeRepository episodeRepository;

    /**
     * GET /episode/all : get all episodes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body the page with
     *         episodes in content
     */
    @GetMapping(path="/all")
	public ResponseEntity<Page<Episode>> episodeAll(Pageable pageable) {
		logger.info("GET request: get all episodes with {} results per page", pageable.getPageSize());

        final Page<Episode> page = episodeRepository.findAll(pageable);

        return ResponseEntity.ok().body(page);
	}

    /**
     * GET /episode/id/:id : get the "id" episode.
     *
     * @param id the id of the episode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the episode, or
     *         with status 404 (Not Found)
     */
	@GetMapping(value = "/id/{id}")
	@ResponseBody
	public ResponseEntity<Episode> getEpisodeById(@PathVariable("id") long id) {
		logger.info("GET request: get episode with id {}", id);

        Optional<Episode> episode = episodeRepository.findById(id);

		return ResponseUtil.wrapOrNotFound(episode);
    }

    /**
     * GET /episode/seasonId/:id : get all episodes with season id "id".
     *
     * @param id the season "id" of the episodes we want to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the episodes in
     *         content
     */
    @GetMapping(value = "/seasonId/{id}")
	@ResponseBody
	public List<Episode> getEpisodesBySeasonId(@PathVariable("id") long id) {
    	logger.info("GET request: get all episode from season with id {}", id);

        return episodeRepository.findBySeasonId(id);
    }

}
