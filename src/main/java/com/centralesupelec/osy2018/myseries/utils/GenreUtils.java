package com.centralesupelec.osy2018.myseries.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.repository.GenreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreUtils {

    @Autowired
    private GenreRepository genreRepository;

    public Map<Genre, Double> getMapGenreDouble(Map<Long, Double> mapGenreIdDouble) {
        Map<Genre, Double> map = new HashMap<>();

        for (Long id : mapGenreIdDouble.keySet()) {
            Optional<Genre> genre = this.genreRepository.findById(id);

            if (genre.isPresent()) {
                map.put(genre.get(), mapGenreIdDouble.get(id));
            }
        }

        return map;
    }
}
