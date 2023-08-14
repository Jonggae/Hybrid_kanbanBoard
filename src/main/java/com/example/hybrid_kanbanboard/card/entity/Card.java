package com.example.hybrid_kanbanboard.card.entity;

import com.example.hybrid_kanbanboard.card.dto.*;
import com.example.hybrid_kanbanboard.cardUser.entity.CardUser;
import com.example.hybrid_kanbanboard.check.entity.Check;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import com.example.hybrid_kanbanboard.comment.entity.Comment;
import com.example.hybrid_kanbanboard.notification.utility.NotificationEntityListener;
import com.example.hybrid_kanbanboard.user.entity.TimeStamped;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
@EntityListeners(NotificationEntityListener.class)
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
    private Long position;

    @Column
    private LocalDateTime dueDate;

    // 수정해야댐~~~
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "column_id")
    private Columns columns;

    @OneToMany(mappedBy = "card")
    private List<Check> checkList;

    @OneToMany(mappedBy = "card")
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card", cascade = CascadeType.ALL)
    private List<CardUser> cardUsers = new ArrayList<>();

    public Card(CardRequestDto requestDto,User user) {
        this.user = user;
        this.name = requestDto.getName();
        this.description = requestDto.getDescription();
        this.color = requestDto.getColor();
        this.position = requestDto.getPosition();
        this.dueDate = requestDto.getDueDate();

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

    public void addComments(Comment comment) {
        this.comments.add(comment);
        comment.setCard(this);
    }

    public void addCheck(Check check) {
        this.checkList.add(check);
        check.setCard(this);
    }
}
