package com.centralesupelec.osy2018.myseries.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ActorEpisode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String characterName;
	
	@ManyToOne
	@JoinColumn(name = "actor_id")
	private Actor actor;
	
	@ManyToOne
	@JoinColumn(name = "episode_id")
	private Episode episode;

	
	public ActorEpisode(long id, String characterName) {
		this.id = id;
		this.characterName = characterName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	
	
}
