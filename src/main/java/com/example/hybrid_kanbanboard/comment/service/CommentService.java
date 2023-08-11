package com.example.hybrid_kanbanboard.comment.service;

import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.card.service.CardService;
import com.example.hybrid_kanbanboard.comment.dto.CommentListResponseDto;
import com.example.hybrid_kanbanboard.comment.dto.CommentRequestDto;
import com.example.hybrid_kanbanboard.comment.dto.CommentResponseDto;
import com.example.hybrid_kanbanboard.comment.entity.Comment;
import com.example.hybrid_kanbanboard.comment.repository.CommentRepository;
import com.example.hybrid_kanbanboard.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CardService cardService;

    @Transactional
    public void createComment(Long cardId , User user, CommentRequestDto commentRequestDto) {
        Card card = cardService.findCard(cardId);
        //받아온 user를 UserBoard에 있는지 조건문처리
        Comment comment = new Comment(commentRequestDto,card);
        card.addComments(comment);

        commentRepository.save(comment);
    }

    @Transactional
    public CommentResponseDto getComment(Long commentId) {
        Comment comment = findComment(commentId);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentListResponseDto getComments() {
        List<CommentResponseDto> commentListResponseDto = commentRepository.findAllByOrderByModifiedAtDesc()
                .stream().map(CommentResponseDto::new).collect(Collectors.toList());
        return new CommentListResponseDto(commentListResponseDto);
    }

    @Transactional
    public void updateComment(Long commentId, User user, CommentRequestDto commentRequestDto) {
        Comment comment = findComment(commentId);

        if(!comment.getUser().getUserName().equals(user.getUserName())) {
            //에외처리
        }
        comment.updateComment(commentRequestDto);
    }
    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId);

        if(!comment.getUser().getUserName().equals(user.getUserName())) {
            //에외처리
        }
        commentRepository.delete(comment);
    }

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
    }
}
