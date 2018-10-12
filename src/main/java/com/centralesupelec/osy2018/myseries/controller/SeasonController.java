package com.centralesupelec.osy2018.myseries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.*;
import com.centralesupelec.osy2018.myseries.repository.*;

@Controller
@RequestMapping(path="/api/season")
public class SeasonController {

	@Autowired
	private SeasonRepository seasonRepository;

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Season> getSeasonById(@PathVariable("id") long id) {
   		return seasonRepository.findById(id);
    }
    
    @RequestMapping(value = "/serie-id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Season> getSeasonsBySerieId(@PathVariable("id") long id) {
   		return seasonRepository.findBySerieId(id);
    }


}
