package com.example.hybrid_kanbanboard.checkList.entity;

import com.example.hybrid_kanbanboard.check.entity.Check;
import com.example.hybrid_kanbanboard.checkList.dto.CheckListRequestDto;
import com.example.hybrid_kanbanboard.user.entity.TimeStamped;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CheckList extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkListId;

    @Column
    private String description;

    @Column
    private boolean isCompleted;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "check_id")
    private Check check;

    public CheckList(CheckListRequestDto checkListRequestDto) {
        this.description = checkListRequestDto.getDescription();
        this.isCompleted = false;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public void setIsCompleted() {
        this.isCompleted = !isCompleted;
    }

    public void updateCheckList(CheckListRequestDto checkListRequestDto) {
        this.description = checkListRequestDto.getDescription();
//        this.isCompleted;
    }
}
