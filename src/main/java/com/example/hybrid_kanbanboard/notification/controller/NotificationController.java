package com.example.hybrid_kanbanboard.notification.controller;

import com.example.hybrid_kanbanboard.notification.entity.Notification;
import com.example.hybrid_kanbanboard.notification.serivce.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hybrid/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @PutMapping("/read/{notificationId}")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unread/{userId}")
    public List<Notification> getUnreadUserNotifications(@PathVariable Long userId) {
        return notificationService.getUnreadUserNotifications(userId);
    }
}