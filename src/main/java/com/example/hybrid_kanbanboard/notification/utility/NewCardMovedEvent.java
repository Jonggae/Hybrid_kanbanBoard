package com.example.hybrid_kanbanboard.notification.utility;

import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import org.springframework.context.ApplicationEvent;

public class NewCardMovedEvent extends ApplicationEvent {
    private final Card card;
    private final Columns oldColumn;
    private final Columns newColumn;

    public NewCardMovedEvent(Object source, Card card, Columns oldColumn, Columns newColumn) {
        super(source);
        this.card = card;
        this.oldColumn = oldColumn;
        this.newColumn = newColumn;
    }

    public Card getCard() {
        return card;
    }

    public Columns getOldColumn() {
        return oldColumn;
    }

    public Columns getNewColumn() {
        return newColumn;
    }
}