package com.centralesupelec.osy2018.myseries.models.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class UserEpisodeDTO {

    private Long userId;

    private Long episodeId;

    @Min(0)
    @Max(5)
    private int rate;

    private boolean seen;

	public UserEpisodeDTO() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(Long episodeId) {
		this.episodeId = episodeId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}



}
