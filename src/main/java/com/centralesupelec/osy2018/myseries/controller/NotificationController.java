package com.centralesupelec.osy2018.myseries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.*;
import com.centralesupelec.osy2018.myseries.repository.*;

@Controller
@RequestMapping(path="/api/notification")
public class NotificationController {

	@Autowired
	private NotificationRepository notificationRepository;


	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Notification> getNotificationById(@PathVariable("id") long id) {
   		return notificationRepository.findById(id);
    }
    
    @RequestMapping(value = "/user-id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Notification> getNotificationsByUserId(@PathVariable("id") long id) {
   		return notificationRepository.findByUserId(id);
    }

    


}
