package com.example.hybrid_kanbanboard.cardUser.entity;

import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "card_user")
@NoArgsConstructor
public class CardUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;


    public CardUser(User user, Card card) {
        this.user = user;
        this.card = card;
        user.getCardUsers().add(this);
        card.getCardUsers().add(this);
    }


    public void cancel() {
        user.getCardUsers().remove(this);
        card.getCardUsers().remove(this);
    }
}
