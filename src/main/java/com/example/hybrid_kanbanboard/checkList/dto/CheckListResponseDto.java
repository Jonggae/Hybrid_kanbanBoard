package com.example.hybrid_kanbanboard.checkList.dto;

import com.example.hybrid_kanbanboard.checkList.entity.CheckList;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CheckListResponseDto {
    private String description;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CheckListResponseDto(CheckList checkList) {
        this.description = checkList.getDescription();
        this.isCompleted = checkList.isCompleted();
        this.createdAt = checkList.getCreatedAt();
        this.modifiedAt = checkList.getModifiedAt();
    }
}
