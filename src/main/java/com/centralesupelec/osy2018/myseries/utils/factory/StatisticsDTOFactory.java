package com.centralesupelec.osy2018.myseries.utils.factory;

import com.centralesupelec.osy2018.myseries.models.dto.StatisticsDTO;

import java.math.BigInteger;
import java.util.List;

public class StatisticsDTOFactory {

    public static StatisticsDTO createStatisticsDTO(int episodeSeenCount, int serieInWatchlistCount) {
        StatisticsDTO newStatisticsDTO = new StatisticsDTO();

        newStatisticsDTO.setEpisodeSeenCount(episodeSeenCount);
        newStatisticsDTO.setSerieInWatchlistCount(serieInWatchlistCount);

        return newStatisticsDTO;
    }

    public static StatisticsDTO createStatisticsDTO(int episodeSeenCount, int serieInWatchlistCount, List<Object[]> serieByGenreCount) {
        StatisticsDTO newStatisticsDTO = new StatisticsDTO();

        newStatisticsDTO.setEpisodeSeenCount(episodeSeenCount);
        newStatisticsDTO.setSerieInWatchlistCount(serieInWatchlistCount);
        newStatisticsDTO.setSerieByGenreCount(serieByGenreCount);

        return newStatisticsDTO;
    }

    public static StatisticsDTO createStatisticsDTO(int episodeSeenCount, int serieInWatchlistCount,
                                                    List<Object[]> serieByGenreCount, BigInteger totalTime) {
        StatisticsDTO newStatisticsDTO = new StatisticsDTO();

        newStatisticsDTO.setEpisodeSeenCount(episodeSeenCount);
        newStatisticsDTO.setSerieInWatchlistCount(serieInWatchlistCount);
        newStatisticsDTO.setSerieByGenreCount(serieByGenreCount);
        newStatisticsDTO.setTotalWatchingTime(totalTime);

        return newStatisticsDTO;
    }
}
