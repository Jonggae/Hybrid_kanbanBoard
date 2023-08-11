package com.example.hybrid_kanbanboard.comment.entity;

import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.comment.dto.CommentRequestDto;
import com.example.hybrid_kanbanboard.user.entity.TimeStamped;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cardId")
    private Card card;
    public Comment(CommentRequestDto commentRequestDto,Card card) {
        this.text = commentRequestDto.getText();
        this.card = card;
    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.text = commentRequestDto.getText();
    }
}
