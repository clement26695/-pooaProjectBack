package com.centralesupelec.osy2018.myseries.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class MovieDBImporter {
	
	@Autowired
	private SerieRepository serieRepository;
	
	public void showImporter() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get("https://api.themoviedb.org/3/tv/popular")
					  .header("accept", "application/json")
					  .queryString("language","en-US")
					  .queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
					  .asJson();
			JSONObject jsonObject = jsonResponse.getBody().getObject();
			
			JSONArray shows = jsonObject.getJSONArray("results");
			shows.forEach(s -> {
				JSONObject show = (JSONObject) s;
				Serie serie = new Serie(show.getString("original_name"), show.getString("overview").substring(0,50));
				
				this.serieRepository.save(serie);
			});

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
