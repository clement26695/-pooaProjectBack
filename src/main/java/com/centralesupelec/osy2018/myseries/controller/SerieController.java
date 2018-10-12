package com.centralesupelec.osy2018.myseries.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centralesupelec.osy2018.myseries.utils.MovieDBImporter;

@Controller
@RequestMapping(path="/api")
public class SerieController {
	
	private final MovieDBImporter movieDBImporter;
	
	public SerieController(MovieDBImporter movieDBImporter) {
		this.movieDBImporter = movieDBImporter;
	}


	@GetMapping(path="/series-refresh")
	public @ResponseBody String refresh() {
		this.movieDBImporter.showImporter(1);
		return "Saved";
	}

}
