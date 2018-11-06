package com.centralesupelec.osy2018.myseries.utils.api_importer;

import com.centralesupelec.osy2018.myseries.config.Constants;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SerieImporter {

    private SerieRepository serieRepository;

    public SerieImporter(SerieRepository serieRepository) {
        super();
        this.serieRepository = serieRepository;
    }

    public void importSerie(int pageLimit, boolean update) {
        int page = 1;

        while (page <= pageLimit) {
            String url = Constants.movieAPIbaseURL + "/popular";

            try {
                HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
                        .queryString("language", "en-US").queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
                        .queryString("page", page).asJson();
                JSONObject jsonObject = jsonResponse.getBody().getObject();

                JSONArray shows = jsonObject.getJSONArray("results");
                shows.forEach(s -> {
                    JSONObject show = (JSONObject) s;

                    Long id = show.getLong("id");
                    Optional<Serie> databaseSerie = this.serieRepository.findByTmdbId(id);

                    if (!databaseSerie.isPresent() || update == true) {
                        Serie serie;
                        if (databaseSerie.isPresent()) {
                            serie = databaseSerie.get();
                        } else {
                            serie = new Serie();
                        }

                        serie.setTmdbId(id);

                        String key = "overview";
                        if (!show.isNull(key)) {
                            String description = show.getString(key);
                            if (description.length() > 2000) {
                                description = description.substring(0, 2000);
                            }

                            serie.setDescription(description);
                        }

                        key = "name";
                        if (!show.isNull(key)) {
                            serie.setName(show.getString(key));
                        }

                        key = "poster_path";
                        if (!show.isNull(key)) {
                            serie.setImage(show.getString(key));
                        }

                        System.out.println("serie importation : " + serie.getName());
                        this.serieRepository.save(serie);
                    }
                });

            } catch (UnirestException e) {
                e.printStackTrace();
            } finally {
                page += 1;
            }
        }
    }

    public void importEpisodeRunTime(Serie serie) {
        String url = Constants.movieAPIbaseURL + "/" + serie.getTmdbId();
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
                    .queryString("language", "en-US").queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
                    .asJson();
            JSONObject jsonObject = jsonResponse.getBody().getObject();

            String key = "episode_run_time";
            if (!jsonObject.isNull(key)) {
                JSONArray episodeRunTimes = jsonObject.getJSONArray(key);
                if (episodeRunTimes.length() != 0) {
                    serie.setEpisodeRunTime(episodeRunTimes.getInt(0));
                }
            }

            this.serieRepository.save(serie);

        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
