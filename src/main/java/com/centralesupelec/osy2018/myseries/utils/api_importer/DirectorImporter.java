package com.centralesupelec.osy2018.myseries.utils.api_importer;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.config.Constants;
import com.centralesupelec.osy2018.myseries.models.Director;
import com.centralesupelec.osy2018.myseries.models.Episode;
import com.centralesupelec.osy2018.myseries.repository.DirectorRepository;
import com.centralesupelec.osy2018.myseries.repository.EpisodeRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DirectorImporter {

    private DirectorRepository directorRepository;
    private EpisodeRepository episodeRepository;

    public DirectorImporter(DirectorRepository directorRepository, EpisodeRepository episodeRepository) {
        super();
        this.directorRepository = directorRepository;
        this.episodeRepository = episodeRepository;
    }

    public void importDirector(Episode episode) {
        String url = Constants.baseURL + "/" + episode.getSeason().getSerie().getTmdbId() + "/season/"
                + episode.getSeason().getSeasonNumber() + "/episode/" + episode.getEpisodeNumber() + "/credits";
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
                    .queryString("language", "en-US").queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
                    .asJson();
            JSONObject jsonObject = jsonResponse.getBody().getObject();

            String keyCrew = "crew";
            if (!jsonObject.isNull(keyCrew)) {

                JSONArray crewResults = jsonObject.getJSONArray("crew");

                crewResults.forEach(c -> {
                    JSONObject crewTMDB = (JSONObject) c;

                    String key = "job";
                    if (!crewTMDB.isNull(key)) {

                        if (crewTMDB.getString(key).equals("Director")) {
                            Director director = new Director();

                            Long id = crewTMDB.getLong("id");
                            Optional<Director> directorOptional = this.directorRepository.findByTmdbId(id);

                            if (directorOptional.isPresent()) {
                                director = directorOptional.get();

                            } else {
                                director.setTmdbId(id);

                                key = "name";
                                if (!crewTMDB.isNull(key)) {
                                    String name = crewTMDB.getString(key);
                                    System.out.println("Director : " + name);
                                    int index = name.lastIndexOf(" ");
                                    String firstName = "", lastName = "";

                                    if (index != -1) {
                                        firstName = name.substring(0, index);
                                        lastName = name.substring(index + 1);
                                    } else {
                                        lastName = name;
                                    }

                                    director.setFirstName(firstName);
                                    director.setLastName(lastName);

                                    key = "profile_path";
                                    if (!crewTMDB.isNull(key)) {
                                        String imagePath = crewTMDB.getString(key);
                                        director.setImage(imagePath);
                                    }
                                }
                                this.directorRepository.save(director);
                            }

                            episode.setDirector(director);
                            this.episodeRepository.save(episode);
                        }
                    }
                });
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
