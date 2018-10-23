package com.centralesupelec.osy2018.myseries.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "genre")
public class Genre {
	
	@Id
	private Long id;
	
	private String name;

	@ManyToMany(mappedBy = "genres")
	private Set<Serie> series;
	
	public Genre() {
		super();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Set<Serie> getSeries() {
		return series;
	}


	public void setSeries(Set<Serie> series) {
		this.series = series;
	}
	
	
	
}
