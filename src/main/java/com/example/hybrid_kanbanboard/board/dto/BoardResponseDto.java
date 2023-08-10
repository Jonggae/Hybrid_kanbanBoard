package com.example.hybrid_kanbanboard.board.dto;

import com.example.hybrid_kanbanboard.board.entity.Board;
import com.example.hybrid_kanbanboard.column.dto.ColumnResponseDto;
import com.example.hybrid_kanbanboard.user.dto.UserRequestDto;
import com.example.hybrid_kanbanboard.user.dto.UserResponseDto;
import com.example.hybrid_kanbanboard.userBoard.UserBoard;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long BoardId;
    private String description;
    private String boardName;
    private String boardMaker;
    private List<ColumnResponseDto> columnList;
    private List<UserResponseDto> userList = new ArrayList<>();

    public BoardResponseDto(Board board) {
        this.BoardId = board.getBoardId();
        this.description= board.getDescription();
        this.boardName = board.getBoardName();
        this.boardMaker = board.getUser().getUserName();
        this.columnList = board.getColumnList().stream()
                .map(ColumnResponseDto::new)
                .toList();
//        this.userList = board.getUserBoards().stream()
//                .map(UserResponseDto::new)
//                .toList();

        for (UserBoard userBoard : board.getUserBoards()) {
            userList.add(new UserResponseDto(userBoard.getCollaborator()));
        }

    }
}
