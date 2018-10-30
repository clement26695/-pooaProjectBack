package com.centralesupelec.osy2018.myseries.controller;

import com.centralesupelec.osy2018.myseries.models.dto.UserEpisodeDTO;
import com.centralesupelec.osy2018.myseries.utils.exceptions.EpisodeNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.exceptions.UserNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.factory.UserEpisodeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/api")
public class UserEpisodeController {

    @Autowired
    private UserEpisodeFactory userEpisodeFactory;

    @PostMapping(value = "/episode/rate")
    // public ResponseEntity<Void> rateEpisode(@RequestBody Long userId, @PathVariable Long episodeId, @RequestBody int rate) {
    public ResponseEntity<Void> rateEpisode(@RequestBody UserEpisodeDTO userEpisodeDTO) {

        try {
            this.userEpisodeFactory.updateOrCreateAndSaveUserEpisode(userEpisodeDTO.getUserId(),
                    userEpisodeDTO.getEpisodeId(), userEpisodeDTO.getRate());
        } catch (EpisodeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);

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
