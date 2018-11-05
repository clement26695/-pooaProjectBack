package com.centralesupelec.osy2018.myseries.utils;

import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.utils.factory.NotificationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationUtils {

    private Logger logger = LoggerFactory.getLogger(NotificationUtils.class);

    private SerieRepository serieRepository;

    private UserRepository userRepository;

    private NotificationFactory notificationFactory;

    public NotificationUtils(SerieRepository serieRepository, UserRepository userRepository, NotificationFactory notificationFactory) {
        this.serieRepository = serieRepository;
        this.userRepository = userRepository;
        this.notificationFactory = notificationFactory;
    }

    @Scheduled(cron = "0 0 0 * * ?")
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
