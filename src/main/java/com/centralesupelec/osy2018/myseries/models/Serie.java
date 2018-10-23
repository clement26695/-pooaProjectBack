package com.centralesupelec.osy2018.myseries.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "serie")
public class Serie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String description;
	private String image;
	
	@OneToMany(mappedBy = "serie")
	private Set<Season> seasons;
	
	@ManyToMany(mappedBy = "series")
	private Set<Watchlist> watchlists;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "serie_genre", joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "serie_id", referencedColumnName = "id"))
	private Set<Genre> genres;
	
	public Serie(){
		
	}
	
	public Serie(String name, String description) {
		this.name = name;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(Set<Season> seasons) {
		this.seasons = seasons;
	}

	public Set<Watchlist> getWatchlists() {
		return watchlists;
	}

	public void setWatchlists(Set<Watchlist> watchlists) {
		this.watchlists = watchlists;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}
	
	
	
}
