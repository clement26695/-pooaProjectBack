package com.centralesupelec.osy2018.myseries.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "director")
public class Director extends Person {

    public Director() {
    }

    public Director(String firstName, String lastName, LocalDate birthday, String image) {
        super(firstName, lastName, birthday, image);
    }

}
