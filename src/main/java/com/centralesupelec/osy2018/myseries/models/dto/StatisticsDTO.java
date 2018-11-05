package com.centralesupelec.osy2018.myseries.models.dto;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Genre;

public class StatisticsDTO {

    private int episodeSeenCount;
    private int serieInWatchlistCount;
    private List<Object[]> serieByGenreCount;
    private BigInteger totalWatchingTime;

    /**
     * @return the episodeSeenCount
     */
    public int getEpisodeSeenCount() {
        return episodeSeenCount;
    }

    /**
     * @return the totalWatchingTime
     */
    public BigInteger getTotalWatchingTime() {
        return totalWatchingTime;
    }

    /**
     * @param totalTime the totalWatchingTime to set
     */
    public void setTotalWatchingTime(BigInteger totalTime) {
        this.totalWatchingTime = totalTime;
    }

    /**
     * @return the serieByGenreCount
     */
    public List<Object[]> getSerieByGenreCount() {
        return serieByGenreCount;
    }

    /**
     * @param serieByGenreCount the serieByGenreCount to set
     */
    public void setSerieByGenreCount(List<Object[]> serieByGenreCount) {
        this.serieByGenreCount = serieByGenreCount;
    }

    /**
     * @return the serieInWatchlistCount
     */
    public int getSerieInWatchlistCount() {
        return serieInWatchlistCount;
    }

    /**
     * @param serieInWatchlistCount the serieInWatchlistCount to set
     */
    public void setSerieInWatchlistCount(int serieInWatchlistCount) {
        this.serieInWatchlistCount = serieInWatchlistCount;
    }

    /**
     * @param episodeSeenCount the episodeSeenCount to set
     */
    public void setEpisodeSeenCount(int episodeSeenCount) {
        this.episodeSeenCount = episodeSeenCount;
    }


}
