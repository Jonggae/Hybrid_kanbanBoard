package com.example.hybrid_kanbanboard.board.entity;

import com.example.hybrid_kanbanboard.board.dto.BoardRequestDto;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BoardId; // board 번호

    @Column(nullable = false)
    private String description; // board 설명

    @Column(nullable = false, unique = true)
    private String name; // board 이름

    @ManyToOne
    @JoinColumn
    private User user;

    public Board(BoardRequestDto requestDto, User user) {
        this.user = user;
        this.description = requestDto.getDescription();
        this.name = requestDto.getName();
    }
}
