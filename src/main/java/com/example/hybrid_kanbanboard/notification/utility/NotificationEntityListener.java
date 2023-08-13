package com.example.hybrid_kanbanboard.notification.utility;

import com.example.hybrid_kanbanboard.board.entity.Board;
import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import com.example.hybrid_kanbanboard.comment.entity.Comment;
import com.example.hybrid_kanbanboard.notification.repository.NotificationRepository;
import com.example.hybrid_kanbanboard.notification.entity.Notification;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.context.event.EventListener;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationEntityListener {

    private NotificationRepository getNotificationRepository() {
        return ApplicationContextProvider.getApplicationContext().getBean(NotificationRepository.class);
    }

    @PostPersist
    public void onPostPersist(Object object) {
        createNotification(object, ActionType.CREATED);
    }

    @PostUpdate
    public void onPostUpdate(Object object) {
        createNotification(object, ActionType.UPDATED);
    }

    @PostRemove
    public void onPostRemove(Object object) {
        createNotification(object, ActionType.REMOVED);
    }

    private String createActionTypeText(ActionType actionType) {
        switch (actionType) {
            case CREATED:
                return "생성";
            case UPDATED:
                return "수정";
            case REMOVED:
                return "삭제";
            default:
                throw new IllegalStateException("예상되지 않은 값: " + actionType);
        }
    }

    private void createNotification(Object target, ActionType actionType) {
        String actionText = createActionTypeText(actionType);
        if (target instanceof Card) {
            Card card = (Card) target;
            String message = "카드 '" + card.getName() + "'이(가) " + actionText + "되었습니다.";
            List<User> users = card.getColumns().getBoard().getBoardMembers();
            for (User user : users) {
                createNotificationForUser(user, message);
            }

        } else if (target instanceof Comment) {
            Comment comment = (Comment) target;
            String message = "카드에 댓글이 " + actionText + "되었습니다.";
            List<User> users = comment.getCard().getColumns().getBoard().getBoardMembers();
            for (User user : users) {
                createNotificationForUser(user, message);
            }
        } else if (target instanceof Columns) {
            Columns columns = (Columns) target;
            String message = "컬럼 '" + columns.getColumnName() + "'이(가) " + actionText + "되었습니다.";
            List<User> users = columns.getBoard().getBoardMembers();
            for (User user : users) {
                createNotificationForUser(user, message);
            }
        } else if (target instanceof Board) {
            Board board = (Board) target;
            String message = "보드 '" + board.getBoardName() + "'이(가) " + actionText + "되었습니다.";
            List<User> users = board.getBoardMembers();
            for (User user : users) {
                createNotificationForUser(user, message);
            }
        }
    }

    private void createNotificationForUser(User user, String message) {
        Notification notification = new Notification(user, message, LocalDateTime.now());
        getNotificationRepository().save(notification);
        System.out.println("알림 대상 사용자: " + user.getUserId() + ", 메시지: " + message);
    }

    @EventListener
    public void handleNewMemberAddedEvent(NewMemberAddedEvent event) {
        // 알림 메시지 생성
        String message = String.format("새로운 멤버 [%s]님이 보드에 추가되었습니다.", event.getNewMember().getUserName());
        // 알림 저장
        Notification notification = new Notification(event.getBoard().getUser(), message, LocalDateTime.now());
        getNotificationRepository().save(notification);
    }

    @EventListener
    public void handleCardMovedEvent(NewCardMovedEvent event) {
        Card card = event.getCard();
        Columns oldColumn = event.getOldColumn();
        Columns newColumn = event.getNewColumn();
        String message = String.format("카드 '%s'가 컬럼 '%s'에서 '%s'로 이동되었습니다.", card.getName(), oldColumn.getColumnName(), newColumn.getColumnName());
        List<User> users = card.getColumns().getBoard().getBoardMembers();

        for (User user : users) {
            createNotificationForUser(user, message);
        }
    }
}