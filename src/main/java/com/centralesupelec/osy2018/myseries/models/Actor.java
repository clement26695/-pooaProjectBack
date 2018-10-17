package com.centralesupelec.osy2018.myseries.models;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "actor")
public class Actor extends Person{

	@OneToMany(mappedBy = "actor")
	private Set<ActorEpisode> actorepisode;
	
	public Actor() {
		super();
	}
	public Actor(String firstName, String lastName, LocalDate birthdate, String image) {
		super(firstName, lastName, birthdate, image);
	}
	
}
