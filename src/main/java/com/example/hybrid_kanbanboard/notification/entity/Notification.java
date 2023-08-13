package com.example.hybrid_kanbanboard.notification.entity;

import com.example.hybrid_kanbanboard.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "message", nullable = false)

    private String message;
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    // 생성자, getter 및 setter 메서드
    public Notification() {
    }

    public Notification(User user, String message, LocalDateTime createdTime) {
        this.user = user;
        this.message = message;
        this.createdTime = createdTime;
        this.isRead = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead= read;
    }
}