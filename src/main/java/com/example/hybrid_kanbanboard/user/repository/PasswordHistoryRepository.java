package com.example.hybrid_kanbanboard.user.repository;

import com.example.hybrid_kanbanboard.user.entity.PasswordHistory;
import com.example.hybrid_kanbanboard.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
    List<PasswordHistory> findTop4ByUserOrderByCreatedAtDesc(User user);
    // PasswordHistory 를 List 로 불러오고 findTop3ByUserOrderByCreatedAtDesc 메서드를 실행
}