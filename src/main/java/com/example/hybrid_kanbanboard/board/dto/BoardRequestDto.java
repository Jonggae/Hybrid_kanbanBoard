package com.example.hybrid_kanbanboard.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private int BoardId;
    private String description;
    private String boardName;
}
