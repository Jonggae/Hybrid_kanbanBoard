package com.example.hybrid_kanbanboard.card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DueDateRequestDto {
    private LocalDateTime dueDate;
}
