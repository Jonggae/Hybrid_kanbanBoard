package com.example.hybrid_kanbanboard.check.repository;

import com.example.hybrid_kanbanboard.check.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> findAllByOrderByModifiedAtDesc();
}
