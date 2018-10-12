package com.centralesupelec.osy2018.myseries.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centralesupelec.osy2018.myseries.models.Actor;
import com.centralesupelec.osy2018.myseries.models.ActorEpisode;
import com.centralesupelec.osy2018.myseries.models.Director;
import com.centralesupelec.osy2018.myseries.models.Episode;
import com.centralesupelec.osy2018.myseries.models.Season;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.ActorEpisodeRepository;
import com.centralesupelec.osy2018.myseries.repository.ActorRepository;
import com.centralesupelec.osy2018.myseries.repository.DirectorRepository;
import com.centralesupelec.osy2018.myseries.repository.EpisodeRepository;
import com.centralesupelec.osy2018.myseries.repository.SeasonRepository;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class MovieDBImporter {
	private final static String baseURL = "https://api.themoviedb.org/3/tv";
	
	@Autowired
	private SerieRepository serieRepository;
	
	@Autowired
	private SeasonRepository seasonRepository;

	@Autowired
	private EpisodeRepository episodeRepository;

	@Autowired
	private DirectorRepository directorRepository;
	
	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private ActorEpisodeRepository actorEpisodeRepository;
	
	public void showImporter(int pageLimit) {
		int page = 1;
		
		ArrayList<Serie> series = new ArrayList<>();
		ArrayList<Integer> seriesId = new ArrayList<>();
		
		while (page <= pageLimit) {
			String url = baseURL + "/popular";
			
			try {	
				HttpResponse<JsonNode> jsonResponse = Unirest.get(url)
						  .header("accept", "application/json")
						  .queryString("language","en-US")
						  .queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
						  .queryString("page", page)
						  .asJson();
				JSONObject jsonObject = jsonResponse.getBody().getObject();
				
				JSONArray shows = jsonObject.getJSONArray("results");
				shows.forEach(s -> {
					JSONObject show = (JSONObject) s;
					
					String description = show.getString("overview");
					
					if (description.length() > 255) {
						description = description.substring(0,255);
					}
					
					Serie serie = new Serie(show.getString("original_name"), description);
					try {
						serie.setImage(show.getString("poster_path"));
					} catch (JSONException e) {
						
					}
					
					this.serieRepository.save(serie);

					this.showInfoImporter(show.getInt("id"), serie);
				});
				
			} catch (UnirestException e) {
				e.printStackTrace();
			} finally {
				page += 1;
			}
		}
		
		for (int i = 0; i < series.size(); i++) {
			this.showInfoImporter(seriesId.get(i), series.get(i));
		}

	}

	public void showInfoImporter(int tmdbShowId, Serie serie) {
		String url = baseURL + "/" + tmdbShowId;
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(url)
				.header("accept", "application/json")
				.queryString("language", "en-US")
				.queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
				.asJson();
			JSONObject jsonObject = jsonResponse.getBody().getObject();

			JSONArray seasons = jsonObject.getJSONArray("seasons");
			seasons.forEach(s -> {
				JSONObject seasonTMDB = (JSONObject) s;
				Season season = new Season(seasonTMDB.getString("name"));
				season.setSerie(serie);
				this.seasonRepository.save(season);

				this.seasonInfoImporter(tmdbShowId, season, seasonTMDB.getInt("season_number"));
			});

		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {

		}
	}
	
	public void seasonInfoImporter(int tmdbShowId, Season season, int seasonNumber) {
		String url = baseURL + "/" + tmdbShowId + "/season/" + seasonNumber;
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(url)
					  .header("accept", "application/json")
					  .queryString("language","en-US")
					  .queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
					  .asJson();
			JSONObject jsonObject = jsonResponse.getBody().getObject();
			
			JSONArray episodesResults = jsonObject.getJSONArray("episodes");
		
			episodesResults.forEach(e -> {
				JSONObject episodeTMDB = (JSONObject) e;				
				Episode episode = new Episode();
				
				String key = "name";
				if (!episodeTMDB.isNull(key)) {
					episode.setName(episodeTMDB.getString(key));
				}

				key = "air_date";
				if (!episodeTMDB.isNull(key)) {
					LocalDate date = LocalDate.parse(episodeTMDB.getString(key));
					LocalTime midnight = LocalTime.of(0, 0);
					ZonedDateTime datetime = ZonedDateTime.of(date, midnight, ZoneId.systemDefault());

					episode.setAirDate(datetime);
				}

				key = "overview";
				if (!episodeTMDB.isNull(key)) {
					String description = episodeTMDB.getString(key);

					if (description.length() > 255) {
						description = description.substring(0, 255);
					}

					episode.setDescription(description);
				}
				
				episode.setSeason(season);

				this.episodeRepository.save(episode);

				this.episodeCreditsImporter(tmdbShowId, episode, seasonNumber, episodeTMDB.getInt("episode_number"));
			});

		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public void episodeCreditsImporter(int tmdbShowId, Episode episode, int seasonNumber, int episodeNumber) {
		String url = baseURL + "/" + tmdbShowId + "/season/" + seasonNumber + "/episode/" + episodeNumber + "/credits";
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
					.queryString("language", "en-US").queryString("api_key", "9c415426d4d9adb84a48883894e3e96a").asJson();
			JSONObject jsonObject = jsonResponse.getBody().getObject();

			JSONArray castResults = jsonObject.getJSONArray("cast");

			castResults.forEach(c -> {
				JSONObject castTMDB = (JSONObject) c;
				Actor actor = new Actor();

				String key = "name";
				if (!castTMDB.isNull(key)) {
					String name = castTMDB.getString(key);
					int index = name.lastIndexOf(" ");
					String firstName = "", lastName = "";

					if (index != -1) {
						firstName = name.substring(0, index);
						lastName = name.substring(index + 1);
					} else {
						lastName = name;
					}
					
					actor.setFirstName(firstName);
					actor.setLastName(lastName);
				}


				key = "profile_path";
				if (!castTMDB.isNull(key)) {
					String imagePath = castTMDB.getString(key);

					actor.setImage(imagePath);
				}

				this.actorRepository.save(actor);

				ActorEpisode actorEpisode = new ActorEpisode();
				actorEpisode.setActor(actor);
				actorEpisode.setEpisode(episode);
				key = "character";
				if (!castTMDB.isNull(key)) {
					String characterName = castTMDB.getString(key);

					actorEpisode.setCharacterName(characterName);
				}

				this.actorEpisodeRepository.save(actorEpisode);
			});

			JSONArray crewResults = jsonObject.getJSONArray("crew");
			
			crewResults.forEach(c -> {
				JSONObject crewTMDB = (JSONObject) c;

				String key = "job";
				if (!crewTMDB.isNull(key)) {
					
					if (crewTMDB.getString(key) == "Director") {
						Director director = new Director();

						key = "name";
						if (!crewTMDB.isNull(key)) {
							String name = crewTMDB.getString(key);
							int index = name.lastIndexOf(" ");
							String firstName = "", lastName = "";

							if (index != -1) {
								firstName = name.substring(0, index);
								lastName = name.substring(index + 1);
							} else {
								lastName = name;
							}

							director.setFirstName(firstName);
							director.setFirstName(lastName);
						}

						key = "profile_path";
						if (!crewTMDB.isNull(key)) {
							String imagePath = crewTMDB.getString(key);
							director.setImage(imagePath);
						}

						director.addEpisode(episode);

						this.directorRepository.save(director);
					}
				}
			});
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {

		}
	}

}
