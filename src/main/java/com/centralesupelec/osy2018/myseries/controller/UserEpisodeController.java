package com.centralesupelec.osy2018.myseries.controller;

import com.centralesupelec.osy2018.myseries.models.UserEpisode;
import com.centralesupelec.osy2018.myseries.models.dto.UserEpisodeDTO;
import com.centralesupelec.osy2018.myseries.models.dto.UserSerieDTO;
import com.centralesupelec.osy2018.myseries.repository.UserEpisodeRepository;
import com.centralesupelec.osy2018.myseries.utils.EpisodeUtils;
import com.centralesupelec.osy2018.myseries.utils.exceptions.EpisodeNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.exceptions.UserNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.factory.UserEpisodeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api")
public class UserEpisodeController {

    Logger logger = LoggerFactory.getLogger(UserEpisodeController.class);

    private UserEpisodeFactory userEpisodeFactory;

    private EpisodeUtils episodeUtils;

    private UserEpisodeRepository userEpisodeRepository;

    public UserEpisodeController(UserEpisodeFactory userEpisodeFactory, EpisodeUtils episodeUtils, UserEpisodeRepository userEpisodeRepository) {
        this.userEpisodeFactory = userEpisodeFactory;
        this.episodeUtils = episodeUtils;
        this.userEpisodeRepository = userEpisodeRepository;
    }

    /**
     * POST /episode/rate : give a rate to a episode.
     *
     * @param userEpisodeDTO {@link UserEpisodeDTO} containing a rate between 1 and
     *                       5, a episodeId, and a userId
     * @return the ResponseEntity with status 204 (NO_CONTENT) if the episode was
     * rated, or with status 404 (Not Found)
     */
    @PostMapping(value = "/episode/rate")
    public ResponseEntity<Void> rateEpisode(@RequestBody UserEpisodeDTO userEpisodeDTO) {
        logger.info("POST request to rate episode {}", userEpisodeDTO.getEpisodeId());

        try {
            this.userEpisodeFactory.updateOrCreateAndSaveUserEpisode(userEpisodeDTO.getUserId(),
                    userEpisodeDTO.getEpisodeId(), userEpisodeDTO.getRate());
        } catch (EpisodeNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /**
     * POST /episode/rate/get : get the rate given by a user to a episode.
     *
     * @param userEpisodeDTO {@link UserEpisodeDTO} containing a episodeId and a
     *                       userId
     * @return the ResponseEntity with status 200 (OK) with body the rate, or with
     * status 404 (Not Found)
     */
    @PostMapping(value = "/episode/rate/get")
    public ResponseEntity<Integer> getRate(@RequestBody UserEpisodeDTO userEpisodeDTO) {
        logger.info("POST request to get episode {} rate by user {}", userEpisodeDTO.getEpisodeId(),
                userEpisodeDTO.getUserId());

        Optional<UserEpisode> userEpisodeResponse = this.userEpisodeRepository
                .findOneByUserIdAndEpisodeId(userEpisodeDTO.getUserId(), userEpisodeDTO.getEpisodeId());

        if (userEpisodeResponse.isPresent()) {
            return ResponseEntity.ok().body(userEpisodeResponse.get().getRate());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /episode/:episodeId/rate/average : get the average rate of a episode.
     *
     * @param episodeId the episodeId of the episode
     * @return the ResponseEntity with status 200 (OK) with body the average rate,
     * or with status 404 (Not Found)
     */
    @GetMapping(value = "/episode/{episodeId}/rate/average")
    public ResponseEntity<Float> getAverageRate(@PathVariable("episodeId") Long episodeId) {
        logger.info("GET request to get episode {} average rate", episodeId);

        Float userEpisodeResponse = this.userEpisodeRepository.getAverageRateForEpisodeId(episodeId);
        if (userEpisodeResponse != null) {
            return ResponseEntity.ok().body(userEpisodeResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST /episode/seen : mark an episode as seen/unseen for a user.
     *
     * @param userEpisodeDTO {@link UserEpisodeDTO} containing a boolean seen, an
     *                       episodeId, and a userId
     * @return the ResponseEntity with status 204 (NO_CONTENT) if the episode was
     * mark as seen/unseen, or with status 404 (Not Found)
     */
    @PostMapping(value = "/episode/seen")
    public ResponseEntity<Void> seenEpisode(@RequestBody UserEpisodeDTO userEpisodeDTO) {
        logger.info("POST request to mark episode {} as seen/unseen", userEpisodeDTO.getEpisodeId());

        try {
            this.userEpisodeFactory.updateOrCreateAndSaveUserEpisode(userEpisodeDTO.getUserId(),
                    userEpisodeDTO.getEpisodeId(), userEpisodeDTO.isSeen());
        } catch (EpisodeNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /**
     * POST /episode/seen : update the rate & seen information between an episode
     * and a user
     *
     * @param userEpisodeDTO {@link UserEpisodeDTO} containing a rate between 1 and
     *                       5, a boolean seen, an episodeId, and a userId
     * @return the ResponseEntity with status 204 (NO_CONTENT) if the episode was
     * updated, or with status 404 (Not Found)
     */
    @PostMapping(value = "/episode/update")
    public ResponseEntity<Void> updateEpisode(@RequestBody UserEpisodeDTO userEpisodeDTO) {
        logger.info("POST request to get update episode {} informations (seen/rate)", userEpisodeDTO.getEpisodeId());

        try {
            this.userEpisodeFactory.updateOrCreateAndSaveUserEpisode(userEpisodeDTO.getUserId(),
                    userEpisodeDTO.getEpisodeId(), userEpisodeDTO.getRate(), userEpisodeDTO.isSeen());
        } catch (EpisodeNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /**
     * POST /episode/extrainformations : get the (seen/rate/averageRate) information
     * for all episode in a serie
     *
     * @param userSerieDTO {@link UserSerieDTO} containing an serieId, and a userId
     * @return the ResponseEntity with status 200 (Ok) with body a map with key
     * episodeId and value the (seen/rate/averageRate) information
     */
    @PostMapping(value = "/episode/extrainformations")
    public ResponseEntity<Map<BigInteger, Map<String, Object>>> test(@RequestBody UserSerieDTO userSerieDTO) {
        logger.info("POST request to get episode from serie {} information (seen/rate/averageRate)",
                userSerieDTO.getSerieId());

        Map<BigInteger, Map<String, Object>> response = this.episodeUtils.getListEpisodeExtra(userSerieDTO);
        return ResponseEntity.ok(response);
    }

}
