package com.example.hybrid_kanbanboard.notification.serivce;

import com.example.hybrid_kanbanboard.notification.entity.Notification;
import com.example.hybrid_kanbanboard.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getUserNotifications(long userId) {
        return notificationRepository.findByUser_userId(userId);
    }

    public void markNotificationAsRead(long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    // NotificationService.java
    public List<Notification> getUnreadUserNotifications(long userId) {
        return notificationRepository.findAllByUser_userIdAndIsReadOrderByCreatedTimeDesc(userId, false);
    }
}