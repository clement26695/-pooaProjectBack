package com.centralesupelec.osy2018.myseries.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.centralesupelec.osy2018.myseries.models.Notification;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.repository.NotificationRepository;

public class NotificationUtils {
	@Autowired
	private NotificationRepository notificationRepository;
	
	public void addNotification(User user, String message) {
		Notification notification = new Notification();
		
		notification.setMessage(message);
		
		notification.setUser(user);
		notificationRepository.save(notification);
	}
}
