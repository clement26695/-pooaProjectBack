package com.centralesupelec.osy2018.myseries.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Actor extends Person{

	@OneToMany(mappedBy = "actor")
	private List<ActorEpisode> actorepisode;
	
	public Actor(String firstName, String lastName, LocalDate birthdate) {
		super(firstName, lastName, birthdate);
		// TODO Auto-generated constructor stub
	}
	
}
