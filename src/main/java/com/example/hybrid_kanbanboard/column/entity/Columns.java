package com.example.hybrid_kanbanboard.column.entity;

import com.example.hybrid_kanbanboard.board.entity.Board;
import com.example.hybrid_kanbanboard.column.dto.ColumnRequestDto;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "columns")
@NoArgsConstructor

public class Columns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ColumnId; // 칼럼 식별 번호

    @jakarta.persistence.Column(name ="name", nullable = false)
    private String columnName; // 칼럼의 이름

    @jakarta.persistence.Column(name = "position", nullable = false)
    private Long columnPosition; // 칼럼의 위치 (가장 왼쪽부터 1, 2, 3...)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "columnMaker")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "boardNumber")
    private Board board;

    public Columns(ColumnRequestDto requestDto, User user, Board board) {
        this.columnName =requestDto.getColumnName();
        this.columnPosition = requestDto.getColumnPosition();
        this.user = user;
        this.board = board;
    }

    public void update(ColumnRequestDto requestDto) {
        this.columnName = requestDto.getColumnName();
        this.columnPosition = requestDto.getColumnPosition();
    }
}
