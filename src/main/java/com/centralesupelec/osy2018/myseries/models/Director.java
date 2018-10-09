package com.centralesupelec.osy2018.myseries.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Director extends Person{

	@OneToMany(mappedBy = "director")
	private List<Episode> episode;
	
	
	public Director(String firstName, String lastName, LocalDate birthdate) {
		super(firstName, lastName, birthdate);
		// TODO Auto-generated constructor stub
	}

}
