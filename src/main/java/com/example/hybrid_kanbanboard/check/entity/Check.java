package com.example.hybrid_kanbanboard.check.entity;

import com.example.hybrid_kanbanboard.check.dto.CheckRequestDto;
import com.example.hybrid_kanbanboard.user.entity.TimeStamped;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "checks")
public class Check extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkId;

    @Column
    private String title;

//    @ManyToOne
//    @JoinColumn
//    private Card card;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Check(CheckRequestDto checkRequestDto) {
        this.title = checkRequestDto.getTitle();
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void updateCheckList(CheckRequestDto checkRequestDto) {
        this.title = checkRequestDto.getTitle();
    }
}
