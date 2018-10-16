package com.centralesupelec.osy2018.myseries.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "director")
public class Director extends Person{

	@OneToMany(mappedBy = "director")
	private List<Episode> episode;
	
	public Director(){

	}
	
	public Director(String firstName, String lastName, LocalDate birthdate, String image) {
		super(firstName, lastName, birthdate, image);
		// TODO Auto-generated constructor stub
	}

	public void addEpisode(Episode e) {
		episode.add(e);
	}

}
