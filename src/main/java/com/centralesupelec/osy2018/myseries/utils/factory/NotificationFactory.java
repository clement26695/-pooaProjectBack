package com.centralesupelec.osy2018.myseries.utils.factory;

import com.centralesupelec.osy2018.myseries.models.Notification;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationFactory {

    private NotificationRepository notificationRepository;

    public NotificationFactory(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(User user, String message) {
        Notification notification = new Notification();

        notification.setMessage(message);

        notification.setUser(user);
        this.notificationRepository.save(notification);
    }

}
