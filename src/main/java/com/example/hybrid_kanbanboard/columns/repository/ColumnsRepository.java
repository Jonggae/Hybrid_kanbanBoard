package com.example.hybrid_kanbanboard.columns.repository;

import com.example.hybrid_kanbanboard.columns.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {

}
