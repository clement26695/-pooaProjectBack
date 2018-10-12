package com.centralesupelec.osy2018.myseries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.*;
import com.centralesupelec.osy2018.myseries.repository.*;

@Controller
@RequestMapping(path="/api/episode")
public class EpisodeController {

	@Autowired
	private EpisodeRepository episodeRepository;

    @GetMapping(path="/all")
	public @ResponseBody Iterable<Episode> seriesAll(){
		return episodeRepository.findAll();
	}


	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Episode> getEpisodeById(@PathVariable("id") long id) {
   		return episodeRepository.findById(id);
    }
    
    @RequestMapping(value = "/season-id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Episode> getEpisodesBySeasonId(@PathVariable("id") long id) {
   		return episodeRepository.findBySeasonId(id);
    }

    


}
