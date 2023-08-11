package com.example.hybrid_kanbanboard.user.repository;

import com.example.hybrid_kanbanboard.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String userEmail);

}