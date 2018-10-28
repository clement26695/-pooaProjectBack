package com.centralesupelec.osy2018.myseries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centralesupelec.osy2018.myseries.utils.MovieDBImporter;

import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.*;
import com.centralesupelec.osy2018.myseries.repository.*;

@Controller
@RequestMapping(path="/api/serie")
public class SerieController {

	private final MovieDBImporter movieDBImporter;

	@Autowired
	private SerieRepository serieRepository;

	public SerieController(MovieDBImporter movieDBImporter) {
		this.movieDBImporter = movieDBImporter;
	}


	@GetMapping(path="/refresh")
	public @ResponseBody String refresh() {
		this.movieDBImporter.showImporter(1);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Serie> seriesAll(){
		return serieRepository.findAll();
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Serie> getSerieById(@PathVariable("id") long id) {
   		return serieRepository.findById(id);
	}

	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	@ResponseBody
	public List<Serie> getSerieByName(@PathVariable("name") String name) {
   		return serieRepository.findByName(name);
	}

}
