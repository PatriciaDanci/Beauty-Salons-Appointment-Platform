package com.se.service;

import com.se.model.Notification;
import com.se.model.User;
import com.se.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationManager {
    private static NotificationManager instance;

    @Autowired
    private NotificationRepository notificationRepository;

    // Private constructor to prevent instantiation
    private NotificationManager() {}

    // Singleton instance
    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    // Method to send notification
    public void sendNotification(String message, User user) {
        Notification notification = new Notification(message, user, false, LocalDateTime.now());
        notificationRepository.save(notification);
    }
}
