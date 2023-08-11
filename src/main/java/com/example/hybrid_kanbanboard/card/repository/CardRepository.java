package com.example.hybrid_kanbanboard.card.repository;

import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Long countCardsByColumns(Columns columns);


    @Transactional
    @Modifying
    @Query("UPDATE Card SET position = position + 1 WHERE position >= :newPosition AND position < :oldPosition AND columns.ColumnId = :id")
    void incrementBelowToPosition(@Param("newPosition") Long newPosition,
                                  @Param("oldPosition") Long oldPosition, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("UPDATE Card SET position = position - 1 WHERE position <= :newPosition AND position > :oldPosition AND columns.ColumnId = :id")
    void decrementAboveToPosition(@Param("newPosition") Long newPosition,
                                  @Param("oldPosition") Long oldPosition, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("UPDATE Card SET position = position - 1 WHERE position >= :position AND columns.ColumnId = :id")
    void decrementBelow(@Param("position") Long position,
                        @Param("id") String id);

    @Transactional
    @Modifying
    @Query("UPDATE Card SET position = position + 1 WHERE position <= :position AND Columns = :columns")
    void incrementBelow(@Param("position") Long position, @Param("columns") Columns columns);
}
