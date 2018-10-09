package com.centralesupelec.osy2018.myseries.models;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Episode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private ZonedDateTime airDate;
	private String description;
	
	@ManyToOne
  	@JoinColumn(name = "director_id")
  	private Director director;
	
	@ManyToOne
	@JoinColumn(name = "season_id")
	private Season season;
	
	@ManyToMany(mappedBy = "episodes")
	private List<Actor> actors;
	
	
  	public Episode(long id, String name, ZonedDateTime airDate, String description) {
		super();
		this.id = id;
		this.name = name;
		this.airDate = airDate;
		this.description = description;
	}
  	
  	
  	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ZonedDateTime getAirDate() {
		return airDate;
	}
	public void setAirDate(ZonedDateTime airDate) {
		this.airDate = airDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
  
  

}