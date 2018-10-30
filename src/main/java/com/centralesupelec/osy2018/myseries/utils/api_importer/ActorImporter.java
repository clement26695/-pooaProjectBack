package com.centralesupelec.osy2018.myseries.utils.api_importer;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.config.Constants;
import com.centralesupelec.osy2018.myseries.models.Actor;
import com.centralesupelec.osy2018.myseries.models.ActorEpisode;
import com.centralesupelec.osy2018.myseries.models.Episode;
import com.centralesupelec.osy2018.myseries.repository.ActorEpisodeRepository;
import com.centralesupelec.osy2018.myseries.repository.ActorRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ActorImporter {

    private ActorRepository actorRepository;
    private ActorEpisodeRepository actorEpisodeRepository;

    public ActorImporter(ActorRepository actorRepository, ActorEpisodeRepository actorEpisodeRepository) {
        super();
        this.actorRepository = actorRepository;
        this.actorEpisodeRepository = actorEpisodeRepository;
    }

    public void importActor(Episode episode) {
        String url = Constants.baseURL + "/" + episode.getSeason().getSerie().getTmdbId() + "/season/"
                + episode.getSeason().getSeasonNumber() + "/episode/" + episode.getEpisodeNumber() + "/credits";
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
                    .queryString("language", "en-US").queryString("api_key", "9c415426d4d9adb84a48883894e3e96a")
                    .asJson();
            JSONObject jsonObject = jsonResponse.getBody().getObject();

            String keyCast = "cast";
            if (!jsonObject.isNull(keyCast)) {
                JSONArray castResults = jsonObject.getJSONArray("cast");

                castResults.forEach(c -> {
                    JSONObject castTMDB = (JSONObject) c;
                    Actor actor = new Actor();

                    Long id = castTMDB.getLong("id");
                    Optional<Actor> actorOptional = this.actorRepository.findByTmdbId(id);

                    if (actorOptional.isPresent()) {
                        actor = actorOptional.get();
                    } else {
                        actor.setTmdbId(id);

                        String key = "name";
                        if (!castTMDB.isNull(key)) {
                            String name = castTMDB.getString(key);
                            System.out.println("Actor : " + name);
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

                            key = "profile_path";
                            if (!castTMDB.isNull(key)) {
                                String imagePath = castTMDB.getString(key);

                                actor.setImage(imagePath);
                            }

                            this.actorRepository.save(actor);
                        }
                    }

                    ActorEpisode actorEpisode = new ActorEpisode();
                    actorEpisode.setActor(actor);
                    actorEpisode.setEpisode(episode);
                    String key = "character";
                    if (!castTMDB.isNull(key)) {
                        String characterName = castTMDB.getString(key);

                        actorEpisode.setCharacterName(characterName);
                    }

                    this.actorEpisodeRepository.save(actorEpisode);

                });
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
