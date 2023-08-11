package com.example.hybrid_kanbanboard.check.entity;

import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.check.dto.CheckRequestDto;
import com.example.hybrid_kanbanboard.checkList.entity.CheckList;
import com.example.hybrid_kanbanboard.user.entity.TimeStamped;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "checks")
public class Check extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkId;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn
    private Card card;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "check")
    private List<CheckList> checkLists = new ArrayList<>();


    public Check(CheckRequestDto checkRequestDto, Card card, User user) {
        this.title = checkRequestDto.getTitle();
        this.card = card;
        this.user = user;
    }


    public void updateCheckList(CheckRequestDto checkRequestDto) {
        this.title = checkRequestDto.getTitle();
    }

    public void addCheckList(CheckList checkList) {
        this.checkLists.add(checkList);
        checkList.setCheck(this);
    }
}
