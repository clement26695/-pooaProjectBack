package com.centralesupelec.osy2018.myseries.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Actor extends Person{

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "episode_actor", joinColumns = @JoinColumn(name = "episode_id", referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "actor_id",referencedColumnName = "id"))
	private List<Episode> episode;
	
	public Actor(String firstName, String lastName, LocalDate birthdate) {
		super(firstName, lastName, birthdate);
		// TODO Auto-generated constructor stub
	}
	
}
