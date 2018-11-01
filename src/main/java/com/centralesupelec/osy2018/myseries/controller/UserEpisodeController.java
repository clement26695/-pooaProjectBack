package com.centralesupelec.osy2018.myseries.controller;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.UserEpisode;
import com.centralesupelec.osy2018.myseries.models.dto.UserEpisodeDTO;
import com.centralesupelec.osy2018.myseries.repository.UserEpisodeRepository;
import com.centralesupelec.osy2018.myseries.utils.exceptions.EpisodeNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.exceptions.UserNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.factory.UserEpisodeFactory;

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
@RequestMapping(path="/api")
public class UserEpisodeController {

    @Autowired
    private UserEpisodeFactory userEpisodeFactory;

    @Autowired
    private UserEpisodeRepository userEpisodeRepository;

    @PostMapping(value = "/episode/rate")
    public ResponseEntity<Void> rateEpisode(@RequestBody UserEpisodeDTO userEpisodeDTO) {

        try {
            this.userEpisodeFactory.updateOrCreateAndSaveUserEpisode(userEpisodeDTO.getUserId(),
                    userEpisodeDTO.getEpisodeId(), userEpisodeDTO.getRate());
        } catch (EpisodeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(value = "/episode/rate/get")
    public ResponseEntity<Integer> getRate(@RequestBody UserEpisodeDTO userEpisodeDTO) {
        Optional<UserEpisode> userEpisodeResponse = this.userEpisodeRepository.findOneByUserIdAndEpisodeId(userEpisodeDTO.getUserId(),
                userEpisodeDTO.getEpisodeId());
        if (userEpisodeResponse.isPresent()) {
            return ResponseEntity.ok().body(userEpisodeResponse.get().getRate());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/episode/{episodeId}/rate/average")
    public ResponseEntity<Float> getAverageRate(@PathVariable("episodeId") Long episodeId) {
        Float userEpisodeResponse = this.userEpisodeRepository.getAverageRateForEpisodeId(episodeId);
        if (userEpisodeResponse != null) {
            return ResponseEntity.ok().body(userEpisodeResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/episode/seen")
    public ResponseEntity<Void> seenEpisode(@RequestBody UserEpisodeDTO userEpisodeDTO) {
        try {
            this.userEpisodeFactory.updateOrCreateAndSaveUserEpisode(userEpisodeDTO.getUserId(), userEpisodeDTO.getEpisodeId(), userEpisodeDTO.isSeen());
        } catch (EpisodeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
