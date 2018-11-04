package com.centralesupelec.osy2018.myseries.controller;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.UserSerie;
import com.centralesupelec.osy2018.myseries.models.dto.UserSerieDTO;
import com.centralesupelec.osy2018.myseries.repository.UserSerieRepository;
import com.centralesupelec.osy2018.myseries.utils.exceptions.SerieNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.exceptions.UserNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.factory.UserSerieFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api")
public class UserSerieController {

    Logger logger = LoggerFactory.getLogger(UserSerieController.class);

    @Autowired
    private UserSerieRepository userSerieRepository;

    @Autowired
    private UserSerieFactory userSerieFactory;

    /**
     * POST /serie/rate : give a rate to a serie.
     *
     * @param userSerieDTO {@link UserSerieDTO} containing a rate between 1 and 5, a
     *                     serieId, and a userId
     * @return the ResponseEntity with status 200 (OK) if the serie was rated, or
     *         with status 404 (Not Found)
     */
    @PostMapping(value = "/serie/rate")
    public ResponseEntity<Void> rateSerie(@RequestBody UserSerieDTO userSerieDTO) {
        logger.info("POST request to rate serie {}", userSerieDTO.getSerieId());

        try {
            this.userSerieFactory.updateOrCreateAndSaveUserSerie(userSerieDTO.getUserId(), userSerieDTO.getSerieId(),
                    userSerieDTO.getRate());
        } catch (SerieNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * POST /serie/rate/get : get the rate given by a user to a serie.
     *
     * @param userSerieDTO {@link UserSerieDTO} containing a serieId and a userId
     * @return the ResponseEntity with status 200 (OK) with body the rate, or with
     *         status 404 (Not Found)
     */
    @PostMapping(value = "/serie/rate/get")
    public ResponseEntity<Integer> getRate(@RequestBody UserSerieDTO userSerieDTO) {
        logger.info("POST request to get serie {} rate by user {}", userSerieDTO.getSerieId(),
                userSerieDTO.getSerieId());

        Optional<UserSerie> userSerieResponse = userSerieRepository.findByUserIdAndSerieId(userSerieDTO.getUserId(),
                userSerieDTO.getSerieId());
        if (userSerieResponse.isPresent()) {
            return ResponseEntity.ok().body(userSerieResponse.get().getRate());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /serie/:serieId/rate/average : get the average rate of a serie.
     *
     * @param serieId the serieId of the serie
     * @return the ResponseEntity with status 200 (OK) with body the average rate,
     *         or with status 404 (Not Found)
     */
    @GetMapping(value = "/serie/{serieId}/rate/average")
    public ResponseEntity<Float> getAverageRate(@PathVariable("serieId") Long serieId) {
        logger.info("GET request to get serie {} average rate", serieId);

        Float userSerieResponse = this.userSerieRepository.getAverageRateForSerieId(serieId);
        if (userSerieResponse != null) {
            return ResponseEntity.ok().body(userSerieResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
