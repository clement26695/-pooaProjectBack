package com.centralesupelec.osy2018.myseries.utils.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centralesupelec.osy2018.myseries.models.Notification;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.repository.NotificationRepository;

@Service
public class NotificationFactory {
	@Autowired
	private NotificationRepository notificationRepository;

	public void createNotification(User user, String message) {
		Notification notification = new Notification();

		notification.setMessage(message);

		notification.setUser(user);
		this.notificationRepository.save(notification);
    }

}
