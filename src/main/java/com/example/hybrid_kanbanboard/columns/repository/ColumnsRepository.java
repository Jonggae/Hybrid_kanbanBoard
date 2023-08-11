package com.example.hybrid_kanbanboard.columns.repository;

import com.example.hybrid_kanbanboard.columns.entity.Columns;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {
    @Modifying
    @Query("UPDATE Columns SET columnPosition = columnPosition + 1 WHERE columnPosition >= :newPosition AND columnPosition < :oldPosition")
    void incrementBelowToPosition(@Param("newPosition") Long newPosition,
                                  @Param("oldPosition") Long oldPosition);

    @Modifying
    @Query("UPDATE Columns SET columnPosition = columnPosition - 1 WHERE columnPosition <= :newPosition AND columnPosition > :oldPosition ")
    void decrementAboveToPosition(@Param("newPosition") Long newPosition,
                                  @Param("oldPosition") Long oldPosition);

    @Modifying
    @Query("UPDATE Columns SET columnPosition = columnPosition - 1 WHERE columnPosition >= :position")
    void decrementBelow(@Param("position") Long position);
}
