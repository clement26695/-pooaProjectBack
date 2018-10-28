package com.centralesupelec.osy2018.myseries.utils.api_importer;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.config.Constants;
import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.GenreRepository;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GenreImporter {

    private SerieRepository serieRepository;

    private GenreRepository genreRepository;

    public GenreImporter(SerieRepository serieRepository, GenreRepository genreRepository) {
		super();
        this.serieRepository = serieRepository;
        this.genreRepository = genreRepository;
	}

    public void importGenre(Serie serie) {
        String url = Constants.baseURL + "/" + serie.getTmdbId();
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
                    .queryString("language", "en-US").queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
                    .asJson();
            JSONObject jsonObject = jsonResponse.getBody().getObject();

            String keyGenre = "genres";
            if (!jsonObject.isNull(keyGenre)) {
                JSONArray genres = jsonObject.getJSONArray(keyGenre);
                genres.forEach(s -> {
                    JSONObject genreTMDB = (JSONObject) s;
                    Genre genre = new Genre();

                    String key = "id";
                    if (!genreTMDB.isNull(key)) {
                        Long id = genreTMDB.getLong(key);

                        Optional<Genre> genreOptional = genreRepository.findById(id);
                        if (genreOptional.isPresent()) {
                            genre = genreOptional.get();
                        } else {
                            genre.setId(id);

                            key = "name";
                            if (!genreTMDB.isNull(key)) {
                                genre.setName(genreTMDB.getString(key));
                            }

                            this.genreRepository.save(genre);
                        }
                    }

                    serie.getGenres().add(genre);

                    this.serieRepository.save(serie);

                });
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
