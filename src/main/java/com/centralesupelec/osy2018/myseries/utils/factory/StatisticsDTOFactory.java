package com.centralesupelec.osy2018.myseries.utils.factory;

import com.centralesupelec.osy2018.myseries.models.dto.StatisticsDTO;

public class StatisticsDTOFactory {

    public static StatisticsDTO createStatisticsDTO(int episodeSeenCount, int serieInWatchlistCount){
        StatisticsDTO newStatisticsDTO = new StatisticsDTO();

        newStatisticsDTO.setEpisodeSeenCount(episodeSeenCount);
        newStatisticsDTO.setSerieInWatchlistCount(serieInWatchlistCount);

        return newStatisticsDTO;
    }
}
