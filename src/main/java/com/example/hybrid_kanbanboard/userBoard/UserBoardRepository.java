package com.example.hybrid_kanbanboard.userBoard;

import com.example.hybrid_kanbanboard.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {

    void deleteAllByCollaborator(User user);
    Optional<UserBoard> findById(Long id);
}
