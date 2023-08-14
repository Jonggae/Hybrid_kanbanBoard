package com.example.hybrid_kanbanboard.notification.repository;

import com.example.hybrid_kanbanboard.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser_userId(long userId);

    List<Notification> findAllByUser_userIdAndIsReadOrderByCreatedTimeDesc(Long userId, boolean isRead);
}