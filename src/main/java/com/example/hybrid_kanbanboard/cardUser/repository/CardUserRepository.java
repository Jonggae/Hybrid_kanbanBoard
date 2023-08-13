package com.example.hybrid_kanbanboard.cardUser.repository;

import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.cardUser.entity.CardUser;
import com.example.hybrid_kanbanboard.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardUserRepository extends JpaRepository <CardUser, Long> {
    Optional<CardUser> findByUserAndCard(User user, Card card);
}
