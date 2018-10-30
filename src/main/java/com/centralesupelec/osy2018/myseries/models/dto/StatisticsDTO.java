package com.centralesupelec.osy2018.myseries.models.dto;

public class StatisticsDTO {

    private int episodeSeenCount;
    private int serieInWatchlistCount;

    /**
     * @return the episodeSeenCount
     */
    public int getEpisodeSeenCount() {
        return episodeSeenCount;
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
