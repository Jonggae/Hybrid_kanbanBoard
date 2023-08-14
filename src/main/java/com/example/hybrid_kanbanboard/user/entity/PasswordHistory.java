package com.example.hybrid_kanbanboard.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class PasswordHistory extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String password;

    public PasswordHistory(User user, String password) {
        this.user = user;
        this.password = password;
    }
    // PasswordHistory 엔티티 클래스는 유저의 비밀번호 변경 이력을 저장
}
