package com.centralesupelec.osy2018.myseries.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.centralesupelec.osy2018.myseries.models.dto.UserSerieDTO;
import com.centralesupelec.osy2018.myseries.repository.UserEpisodeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EpisodeUtils {

    @Autowired
    private UserEpisodeRepository userEpisodeRepository;

    public Map<BigInteger, Map<String, Object>> getListEpisodeExtra(UserSerieDTO userSerieDTO) {
        List<Object[]> resultsAverageRateByEpisode = this.userEpisodeRepository
                .getAverageRateByEpisodeOfSerieId(userSerieDTO.getSerieId());

        Map<BigInteger, BigDecimal> mapAverageRateByEpisode = new HashMap<>();

        resultsAverageRateByEpisode.forEach(entry -> {
            BigInteger key = (BigInteger) entry[0];
            BigDecimal value = (BigDecimal) entry[1];
            mapAverageRateByEpisode.put(key, value);
        });

        Map<BigInteger, Map<String, Object>> episodeMap = new HashMap<>();


        List<Object[]> results = this.userEpisodeRepository
                .getEpisodeUserAndEpisodeBySerieIdAndUserId(userSerieDTO.getSerieId(), userSerieDTO.getUserId());

        BigInteger episodeId;
        Boolean seen;
        Byte rate;
        for (Object[] entry : results) {
            rate = (Byte) entry[0];
            seen = (Boolean) entry[1];
            episodeId = (BigInteger) entry[2];

            Map<String, Object> episodeValues = new HashMap<>();

            episodeValues.put("rate", rate);
            episodeValues.put("seen", seen);
            episodeValues.put("averageRate", mapAverageRateByEpisode.get(episodeId));

            episodeMap.put(episodeId, episodeValues);
        };


        return episodeMap;
    }
}
