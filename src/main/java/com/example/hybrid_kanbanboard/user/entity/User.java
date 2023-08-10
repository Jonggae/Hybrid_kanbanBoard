package com.example.hybrid_kanbanboard.user.entity;

import com.example.hybrid_kanbanboard.user.dto.ProfileUpdateDto;
import com.example.hybrid_kanbanboard.user.dto.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column
    private String myContent;

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
