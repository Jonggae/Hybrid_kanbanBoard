package com.example.hybrid_kanbanboard.card.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardRequestDto {
    private String name;
    private String description;
    private String color;
    private Long position;
    private LocalDateTime dueDate;
}
