package com.centralesupelec.osy2018.myseries.repository;

import java.util.List;

import com.centralesupelec.osy2018.myseries.models.Notification;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface NotificationRepository extends CrudRepository<Notification, Long> {
	List<Notification> findByUserId(long id);

}
