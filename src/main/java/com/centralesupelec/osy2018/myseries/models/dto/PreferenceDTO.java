package com.centralesupelec.osy2018.myseries.models.dto;

import com.centralesupelec.osy2018.myseries.models.Serie;

public class PreferenceDTO {

    private Double score;
    private Serie serie;

    public PreferenceDTO() {}

    /**
     * @return the serie
     */
    public Serie getSerie() {
        return serie;
    }

    /**
     * @return the score
     */
    public Double getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(Serie serie) {
        this.serie = serie;
    }

}
