package com.example.hybrid_kanbanboard.comment.entity;

import com.example.hybrid_kanbanboard.comment.dto.CommentRequestDto;
import com.example.hybrid_kanbanboard.user.entity.TimeStamped;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "cardId")
//    private Card card;
    public Comment(CommentRequestDto commentRequestDto) {
        this.text = commentRequestDto.getText();
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public void setCard(Card card) {
//        this.card = card;
//    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.text = commentRequestDto.getText();
    }
}
