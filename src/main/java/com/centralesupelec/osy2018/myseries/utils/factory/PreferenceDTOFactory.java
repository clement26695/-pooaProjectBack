package com.centralesupelec.osy2018.myseries.utils.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.dto.PreferenceDTO;

public class PreferenceDTOFactory {

    public static PreferenceDTO createPreferenceDTO(Serie serie, Double score) {
        PreferenceDTO preferenceDTO = new PreferenceDTO();

        preferenceDTO.setSerie(serie);
        preferenceDTO.setScore(score);

        return preferenceDTO;
    }

    public static List<PreferenceDTO> createListPreferenceDTO(List<Entry<Serie, Double>> entries) {
        List<PreferenceDTO> preferences = new ArrayList<>();

        for (Entry<Serie, Double> entry : entries) {
            PreferenceDTO preferenceDTO = PreferenceDTOFactory.createPreferenceDTO(entry.getKey(), entry.getValue());
            preferences.add(preferenceDTO);
        }

        return preferences;
    }
}
