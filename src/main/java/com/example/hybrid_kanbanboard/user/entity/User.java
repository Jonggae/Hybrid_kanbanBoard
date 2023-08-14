package com.example.hybrid_kanbanboard.user.entity;

import com.example.hybrid_kanbanboard.board.entity.Board;
import com.example.hybrid_kanbanboard.notification.entity.Notification;
import com.example.hybrid_kanbanboard.cardUser.entity.CardUser;
import com.example.hybrid_kanbanboard.user.dto.ProfileUpdateDto;
import com.example.hybrid_kanbanboard.user.dto.UserRoleEnum;
import com.example.hybrid_kanbanboard.userBoard.UserBoard;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Notification> notifications;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "collaborator", orphanRemoval = true)
    private List<UserBoard> boardUser = new ArrayList<>();

//    @OneToMany(mappedBy = "maker",orphanRemoval = true)
//    private List<Board> boardList = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column
    private String myContent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CardUser> cardUsers = new ArrayList<>();

    public User(String username, String password, String email, String Nick, UserRoleEnum role) {
        this.userName = username;
        this.password = password;
        this.nickname = Nick;
        this.email = email;
        this.role = role;
    }

    public void updateProfile(ProfileUpdateDto profileUpdateDto) {
        this.password = profileUpdateDto.getChangePassword();
        this.nickname = profileUpdateDto.getNickname();
        this.email = profileUpdateDto.getEmail();
        this.myContent = profileUpdateDto.getMyContent();
    }

}
