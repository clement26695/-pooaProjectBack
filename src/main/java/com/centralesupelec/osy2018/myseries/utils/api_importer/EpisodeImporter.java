package com.centralesupelec.osy2018.myseries.utils.api_importer;

import com.centralesupelec.osy2018.myseries.config.Constants;
import com.centralesupelec.osy2018.myseries.models.Episode;
import com.centralesupelec.osy2018.myseries.models.Season;
import com.centralesupelec.osy2018.myseries.repository.EpisodeRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class EpisodeImporter {

    private EpisodeRepository episodeRepository;

    public EpisodeImporter(EpisodeRepository episodeRepository) {
        super();
        this.episodeRepository = episodeRepository;
    }

    public void importEpisode(Season season, boolean update) {


        String url = Constants.movieAPIbaseURL + "/" + season.getSerie().getTmdbId() + "/season/" + season.getSeasonNumber();
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
                    .queryString("language", "en-US").queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
                    .asJson();
            JSONObject jsonObject = jsonResponse.getBody().getObject();

            String keyEpisodes = "episodes";
            if (!jsonObject.isNull(keyEpisodes)) {
                JSONArray episodesResults = jsonObject.getJSONArray("episodes");

                episodesResults.forEach(e -> {
                    JSONObject episodeTMDB = (JSONObject) e;
                    Episode episode = new Episode();

                    Long id = episodeTMDB.getLong("id");
                    Optional<Episode> databaseEpisode = this.episodeRepository.findByTmdbId(id);

                    if (!databaseEpisode.isPresent() || update == true) {
                        if (databaseEpisode.isPresent()) {
                            episode = databaseEpisode.get();
                        }

                        episode.setTmdbId(episodeTMDB.getLong("id"));

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

                            if (description.length() > 2000) {
                                description = description.substring(0, 2000);
                            }

                            episode.setDescription(description);
                        }

                        key = "still_path";
                        if (!episodeTMDB.isNull(key)) {
                            String imageURL = episodeTMDB.getString(key);

                            episode.setImageURL(imageURL);
                        }

                        key = "episode_number";
                        if (!episodeTMDB.isNull(key)) {
                            int episodeNumber = episodeTMDB.getInt(key);

                            episode.setEpisodeNumber(episodeNumber);
                        }

                        episode.setSeason(season);

                        this.episodeRepository.save(episode);
                    }
                });
            }

        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
    }

}
