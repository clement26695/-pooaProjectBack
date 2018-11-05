package com.centralesupelec.osy2018.myseries.controller;

import com.centralesupelec.osy2018.myseries.models.Notification;
import com.centralesupelec.osy2018.myseries.repository.NotificationRepository;
import com.centralesupelec.osy2018.myseries.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/api/notification")
public class NotificationController {

	Logger logger = LoggerFactory.getLogger(NotificationController.class);

	private NotificationRepository notificationRepository;

	public NotificationController(NotificationRepository notificationRepository) {
	    this.notificationRepository = notificationRepository;
    }

    /**
     * GET /notification/id/:id : get the "id" notification.
     *
     * @param id the id of the notification to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notification, or
     *         with status 404 (Not Found)
     */
	@GetMapping(value = "/id/{id}")
	@ResponseBody
	public ResponseEntity<Notification> getNotificationById(@PathVariable("id") long id) {
        logger.info("GET request: get notification with id {}", id);

        Optional<Notification> notification = notificationRepository.findById(id);

        return ResponseUtil.wrapOrNotFound(notification);
    }

    /**
     * GET /notification/user-id/:id : get all notifications with user id "id".
     *
     * @param id the user "id" of the notifications we want to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         notifications in content
     */
    @GetMapping(value = "/user-id/{id}")
	@ResponseBody
	public List<Notification> getNotificationsByUserId(@PathVariable("id") long id) {
        logger.info("GET request: get all notification from user with id {}", id);

        return notificationRepository.findByUserId(id);
    }




}
