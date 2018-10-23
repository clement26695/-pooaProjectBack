package com.centralesupelec.osy2018.myseries.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "director")
public class Director extends Person{

	@OneToMany(mappedBy = "director")
	private Set<Episode> episode;

	public Director(){

	}

	public Director(String firstName, String lastName, LocalDate birthdate, String image) {
		super(firstName, lastName, birthdate, image);
		this.episode = new HashSet<>();
	}

	public Set<Episode> getEpisodes(){
        return episode;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episode = episodes;
    }

}
