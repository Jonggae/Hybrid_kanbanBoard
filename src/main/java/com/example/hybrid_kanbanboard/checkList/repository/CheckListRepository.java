package com.example.hybrid_kanbanboard.checkList.repository;

import com.example.hybrid_kanbanboard.checkList.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
    List<CheckList> findAllByOrderByModifiedAtDesc();
}
