package com.example.hybrid_kanbanboard.notification.utility;

import com.example.hybrid_kanbanboard.board.entity.Board;
import com.example.hybrid_kanbanboard.user.entity.User;

public class NewMemberAddedEvent {
    private User newMember;
    private Board board;

    public NewMemberAddedEvent(User newMember, Board board) {
        this.newMember = newMember;
        this.board = board;
    }

    public User getNewMember() {
        return newMember;
    }

    public Board getBoard() {
        return board;
    }
}