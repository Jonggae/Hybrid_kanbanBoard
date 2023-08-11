package com.example.hybrid_kanbanboard.comment.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CommentListResponseDto {
    List<CommentResponseDto> commentListResponseDto;

    public CommentListResponseDto(List<CommentResponseDto> commentListResponseDto) {
        this.commentListResponseDto = commentListResponseDto;
    }
}
