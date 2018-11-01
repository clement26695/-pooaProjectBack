package com.centralesupelec.osy2018.myseries.controller;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.UserSerie;
import com.centralesupelec.osy2018.myseries.models.dto.UserSerieDTO;
import com.centralesupelec.osy2018.myseries.repository.UserSerieRepository;
import com.centralesupelec.osy2018.myseries.utils.exceptions.SerieNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.exceptions.UserNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.factory.UserSerieFactory;

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

	@Autowired
	private UserSerieRepository userSerieRepository;

    @Autowired
    private UserSerieFactory userSerieFactory;

    @PostMapping(value = "/serie/rate")
    public ResponseEntity<Void> rateEpisode(@RequestBody UserSerieDTO userSerieDTO) {
        try {
            this.userSerieFactory.updateOrCreateAndSaveUserSerie(userSerieDTO.getUserId(),
                userSerieDTO.getSerieId(), userSerieDTO.getRate());
        } catch (SerieNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

	@PostMapping(value = "/serie/rate/get")
	public ResponseEntity<Integer> getRate(@RequestBody UserSerieDTO userSerieDTO) {
		Optional<UserSerie> userSerieResponse = userSerieRepository.findByUserIdAndSerieId(userSerieDTO.getUserId(),
                userSerieDTO.getSerieId());
		if (userSerieResponse.isPresent()) {
			return ResponseEntity.ok().body(userSerieResponse.get().getRate());
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    @GetMapping(value = "/serie/{serieId}/rate/average")
    public ResponseEntity<Float> getAverageRate(@PathVariable("serieId") Long serieId) {
        Float userSerieResponse = this.userSerieRepository.getAverageRateForSerieId(serieId);
        if (userSerieResponse != null) {
            return ResponseEntity.ok().body(userSerieResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
