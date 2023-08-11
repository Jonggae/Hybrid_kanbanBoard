package com.example.hybrid_kanbanboard.check.dto;

import com.example.hybrid_kanbanboard.check.entity.Check;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CheckResponseDto {
    private String title;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public CheckResponseDto(Check check) {
        this.title = check.getTitle();
        this.createdAt = check.getCreatedAt();
        this.modifiedAt = check.getModifiedAt();
    }
}
