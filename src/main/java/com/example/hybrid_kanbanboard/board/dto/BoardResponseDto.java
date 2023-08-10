package com.example.hybrid_kanbanboard.board.dto;

import com.example.hybrid_kanbanboard.board.entity.Board;
import com.example.hybrid_kanbanboard.columns.dto.ColumnsResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardResponseDto {
    private Long BoardId;
    private String description;
    private String boardName;
    private String boardMaker;
    private List<ColumnsResponseDto> columnsList;

    public BoardResponseDto(Board board) {
        this.BoardId = board.getBoardId();
        this.description= board.getDescription();
        this.boardName = board.getBoardName();
        this.boardMaker = board.getUser().getUserName();
        this.columnsList = board.getColumnsList().stream()
                .map(ColumnsResponseDto::new)
                .toList();

    }
}
