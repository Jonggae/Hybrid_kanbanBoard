package com.example.hybrid_kanbanboard.board.entity;

import com.example.hybrid_kanbanboard.board.dto.BoardRequestDto;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import com.example.hybrid_kanbanboard.user.dto.UserRoleEnum;
import com.example.hybrid_kanbanboard.user.entity.User;
import com.example.hybrid_kanbanboard.userBoard.UserBoard;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private String boardName; // board 이름

    // N대 1 : User클래스의 boardMaker(1)가 여러개의 board(N)를 만들 수 있으므로..
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardMaker")
    private User user;

    // 가입한 보드
    // 회원가입한 유저들 중 특정 유저들만 보드에 접근할 수 있도록 하는 방법은 무엇일까?

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    List<UserBoard> userBoards = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Columns> columnList = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public Board(BoardRequestDto requestDto, User user, UserRoleEnum role) {
        this.user = user;
        this.description = requestDto.getDescription();
        this.boardName = requestDto.getBoardName();
        this.userBoards = new ArrayList<>();
        this.role = role;

    }

    public void update(BoardRequestDto requestDto) {
        this.boardName = requestDto.getBoardName();
        this.description = requestDto.getDescription();
        this.role = role;
    }

    public void addColumnList(Columns column) {
        this.columnList.add(column);
        column.setBoard(this);
    }
}
