package com.centralesupelec.osy2018.myseries.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "actor")
public class Actor extends Person {

    public Actor() {
        super();
    }

    public Actor(String firstName, String lastName, LocalDate birthday, String image) {
        super(firstName, lastName, birthday, image);
    }

}
