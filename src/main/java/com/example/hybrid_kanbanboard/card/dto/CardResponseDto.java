package com.example.hybrid_kanbanboard.card.dto;

import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardResponseDto extends MsgResponseDto {
    private String name;
    private String description;
    private String color;
    private Long position;
    private LocalDateTime dueDate;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    public CardResponseDto(Card card) {
        this.name = card.getName();
        this.description = card.getDescription();
        this.color = card.getColor();
        this.position = card.getPosition();
        this.dueDate = card.getDueDate();
        this.createAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();
    }
}
