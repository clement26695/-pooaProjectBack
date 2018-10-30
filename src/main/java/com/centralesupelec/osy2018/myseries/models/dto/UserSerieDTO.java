package com.centralesupelec.osy2018.myseries.models.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class UserSerieDTO {

    private Long userId;

    private Long serieId;

    @Min(0)
    @Max(5)
    private int rate;

	public UserSerieDTO() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSerieId() {
		return serieId;
	}

	public void setSerieId(Long serieId) {
		this.serieId = serieId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
