package com.example.hybrid_kanbanboard.card.entity;

import com.example.hybrid_kanbanboard.board.entity.Board;
import com.example.hybrid_kanbanboard.card.dto.*;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import com.example.hybrid_kanbanboard.user.entity.TimeStamped;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
//@Setter
@NoArgsConstructor
@Table
public class Card extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String color;

    @Column
    private String position;

    @Column
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "columns_id")
    private Columns columns;


    public Card(Columns columns, CardRequestDto requestDto, User user) {
        this.columns = columns;
        this.user = user;
        this.name = requestDto.getName();
        this.description = requestDto.getDescription();
        this.color = requestDto.getColor();
        this.position = requestDto.getPosition();
        this.dueDate = requestDto.getDueDate();
    }

    public Card update(CardRequestDto requestDto) {
        this.name = requestDto.getName();
        this.description = requestDto.getDescription();
        this.color = requestDto.getColor();
        this.position = requestDto.getPosition();
        this.dueDate = requestDto.getDueDate();

        return this;
    }

    public void updateName(NameRequestDto requestDto) {
        this.name = requestDto.getName();
    }

    public void updateDescription(DescriptionRequestDto requestDto) {
        this.description = requestDto.getDescription();
    }

    public void updateColor(ColorRequestDto requestDto) {
        this.color = requestDto.getColor();
    }

    public void updateDueDate(DueDateRequestDto requestDto) {
        this.dueDate = requestDto.getDueDate();
    }
}
