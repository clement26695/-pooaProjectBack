package com.centralesupelec.osy2018.myseries.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "actor")
public class Actor extends Person{

	public Actor() {
		super();
	}
	public Actor(String firstName, String lastName, LocalDate birthdate, String image) {
		super(firstName, lastName, birthdate, image);
	}

}
