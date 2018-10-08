package com.centralesupelec.osy2018.myseries.models;

import java.time.LocalDate;

import javax.persistence.Entity;

@Entity
public class Actor extends Person{

	public Actor(String firstName, String lastName, LocalDate birthdate) {
		super(firstName, lastName, birthdate);
		// TODO Auto-generated constructor stub
	}
	
}
