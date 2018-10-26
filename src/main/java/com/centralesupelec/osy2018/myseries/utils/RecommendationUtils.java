package com.centralesupelec.osy2018.myseries.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;

public class RecommendationUtils {
	
	private SerieRepository serieRepository;
	
	Iterable<Serie> series = serieRepository.findAll();
	
	public void computeRecommendation(Map<Genre, Double> map){
		
		 Map<Serie, Double> scores = new HashMap<Serie, Double>();
		
		 for(Serie serie : series) {
			 double score = 0.0;
		
			 Set<Genre> genres = serie.getGenres();
			 
			 for(Genre genre : genres) {
				 if(map.containsKey(genre)){
					 score = score + map.get(genre);
				 }
			 }
			 
			 scores.put(serie, score);
		
		 }
		 
		 Comparator<Entry<Serie, Double>> comparator = new Comparator<Entry<Serie, Double>>(){
			 public int compare(Entry<Serie, Double> a, Entry<Serie, Double> b) {
				 
				 return a.getValue() > b.getValue() ? 1 : a.getValue() == b.getValue() ? 0 : -1;
			 }
		 };
		 
		 
		 List<Entry<Serie, Double>> list = new ArrayList<>(scores.entrySet());
		 list.sort(comparator);
	}

}
