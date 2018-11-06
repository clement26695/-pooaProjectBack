package com.centralesupelec.osy2018.myseries.utils;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
public class RecommendationUtils {

    private SerieRepository serieRepository;

    public RecommendationUtils(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public List<Entry<Serie, Double>> computeRecommendation(Map<Genre, Double> map) {
        Iterable<Serie> series = this.serieRepository.findAll();

        Map<Serie, Double> scoreSerie = new HashMap<Serie, Double>();

        for (Serie serie : series) {
            double score = 0.0;

            Set<Genre> genres = serie.getGenres();

            for (Genre genre : genres) {
                if (map.containsKey(genre)) {
                    score = score + map.get(genre);
                }
            }

            scoreSerie.put(serie, score);

        }

        Comparator<Entry<Serie, Double>> comparator = new Comparator<Entry<Serie, Double>>() {
            public int compare(Entry<Serie, Double> a, Entry<Serie, Double> b) {

                return a.getValue() < b.getValue() ? 1 : a.getValue().equals(b.getValue()) ? 0 : -1;
            }
        };

        List<Entry<Serie, Double>> entries = new ArrayList<>(scoreSerie.entrySet());
        entries.sort(comparator);

        return entries;
    }

}
