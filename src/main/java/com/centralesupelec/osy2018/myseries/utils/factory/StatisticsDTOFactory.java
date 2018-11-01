package com.centralesupelec.osy2018.myseries.utils.factory;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Genre;
import com.centralesupelec.osy2018.myseries.models.dto.StatisticsDTO;

public class StatisticsDTOFactory {

    public static StatisticsDTO createStatisticsDTO(int episodeSeenCount, int serieInWatchlistCount){
        StatisticsDTO newStatisticsDTO = new StatisticsDTO();

        newStatisticsDTO.setEpisodeSeenCount(episodeSeenCount);
        newStatisticsDTO.setSerieInWatchlistCount(serieInWatchlistCount);

        return newStatisticsDTO;
    }

    public static StatisticsDTO createStatisticsDTO(int episodeSeenCount, int serieInWatchlistCount, List<Map<Genre, Integer>> serieByGenreCount) {
        StatisticsDTO newStatisticsDTO = new StatisticsDTO();

        newStatisticsDTO.setEpisodeSeenCount(episodeSeenCount);
        newStatisticsDTO.setSerieInWatchlistCount(serieInWatchlistCount);
        newStatisticsDTO.setSerieByGenreCount(serieByGenreCount);

        return newStatisticsDTO;
    }

    public static StatisticsDTO createStatisticsDTO(int episodeSeenCount, int serieInWatchlistCount,
            List<Map<Genre, Integer>> serieByGenreCount, Optional<BigInteger> totalTime) {
        StatisticsDTO newStatisticsDTO = new StatisticsDTO();

        newStatisticsDTO.setEpisodeSeenCount(episodeSeenCount);
        newStatisticsDTO.setSerieInWatchlistCount(serieInWatchlistCount);
        newStatisticsDTO.setSerieByGenreCount(serieByGenreCount);
        newStatisticsDTO.setTotalWatchingTime(totalTime);

        return newStatisticsDTO;
    }
}
