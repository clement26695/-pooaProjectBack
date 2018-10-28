package com.centralesupelec.osy2018.myseries.utils;

import java.util.List;

import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.utils.factory.NotificationFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationUtils {

    Logger logger = LoggerFactory.getLogger(NotificationUtils.class);
    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired NotificationFactory notificationFactory;

    @Scheduled(cron = "0 * * * * ?")
    public void notifyUsersForEpisodeTomorrow() {
        logger.info("Create Notification for Tomorrow");

        List<Serie> series = this.serieRepository.findSerieWithEpisodeTomorrow();

        series.forEach(serie -> {
            List<User> users = this.userRepository.findUserWithSerieInWatchlist(serie.getId());

            for (User user : users) {
                notificationFactory.createNotification(user, "A new episode of " + serie.getName() + " will be aired in the next 24h. Be ready !");
            }
        });
    }
}
