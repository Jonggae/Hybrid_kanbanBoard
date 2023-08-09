package com.example.hybrid_kanbanboard.comment.dto;

import com.example.hybrid_kanbanboard.comment.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponseDto {
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
