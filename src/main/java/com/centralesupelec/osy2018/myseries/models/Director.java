package com.centralesupelec.osy2018.myseries.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "director")
public class Director extends Person{

	public Director(){

	}

	public Director(String firstName, String lastName, LocalDate birthdate, String image) {
		super(firstName, lastName, birthdate, image);
		// this.episode = new HashSet<>();
	}
}
