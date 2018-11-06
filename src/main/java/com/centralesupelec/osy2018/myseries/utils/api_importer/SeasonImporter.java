package com.centralesupelec.osy2018.myseries.utils.api_importer;

import com.centralesupelec.osy2018.myseries.config.Constants;
import com.centralesupelec.osy2018.myseries.models.Season;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.SeasonRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeasonImporter {

    private SeasonRepository seasonRepository;

    public SeasonImporter(SeasonRepository seasonRepository) {
        super();
        this.seasonRepository = seasonRepository;
    }

    public void importSeason(Serie serie) {
        String url = Constants.movieAPIbaseURL + "/" + serie.getTmdbId();
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
                    .queryString("language", "en-US").queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
                    .asJson();
            JSONObject jsonObject = jsonResponse.getBody().getObject();

            String keySeasons = "seasons";
            if (!jsonObject.isNull(keySeasons)) {
                JSONArray seasons = jsonObject.getJSONArray(keySeasons);
                seasons.forEach(s -> {
                    JSONObject seasonTMDB = (JSONObject) s;
                    Season season = new Season();

                    Long id = seasonTMDB.getLong("id");
                    Optional<Season> databaseSeason = this.seasonRepository.findByTmdbId(id);

                    if (!databaseSeason.isPresent()) {
                        season.setTmdbId(id);

                        String key = "name";
                        if (!seasonTMDB.isNull(key)) {
                            season.setName(seasonTMDB.getString(key));
                        }

                        key = "season_number";
                        if (!seasonTMDB.isNull(key)) {
                            int seasonNumber = seasonTMDB.getInt(key);
                            season.setSeasonNumber(seasonNumber);
                        }

                        key = "poster_path";
                        if (!seasonTMDB.isNull(key)) {
                            String imageURL = seasonTMDB.getString(key);
                            season.setImageURL(imageURL);
                        }

                        season.setSerie(serie);
                        this.seasonRepository.save(season);
                    }
                });
            }

        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
    }
}
