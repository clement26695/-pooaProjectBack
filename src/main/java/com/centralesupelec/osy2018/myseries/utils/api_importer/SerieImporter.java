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

@Service
public class SerieImporter {

    private SerieRepository serieRepository;


    public SerieImporter(SerieRepository serieRepository) {
		super();
		this.serieRepository = serieRepository;
	}


    public void importSerie(int pageLimit) {
        int page = 1;

        while (page <= pageLimit) {
            String url = Constants.baseURL + "/popular";

            try {
                HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
                        .queryString("language", "en-US").queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
                        .queryString("page", page).asJson();
                JSONObject jsonObject = jsonResponse.getBody().getObject();

                JSONArray shows = jsonObject.getJSONArray("results");
                shows.forEach(s -> {
                    JSONObject show = (JSONObject) s;

                    Serie serie = new Serie();

                    serie.setTmdbId(show.getLong("id"));

                    String key = "overview";
                    if (!show.isNull(key)) {
                        String description = show.getString(key);
                        if (description.length() > 2000) {
                            description = description.substring(0, 2000);
                        }

                        serie.setDescription(description);
                    }

                    key = "original_name";
                    if (!show.isNull(key)) {
                        serie.setName(show.getString(key));
                    }

                    key = "poster_path";
                    if (!show.isNull(key)) {
                        serie.setImage(show.getString(key));
                    }

                    System.out.println("serie importation : " + serie.getName());
                    this.serieRepository.save(serie);

                });

            } catch (UnirestException e) {
                e.printStackTrace();
            } finally {
                page += 1;
            }
        }
    }

}
