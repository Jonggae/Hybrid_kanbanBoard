package com.example.hybrid_kanbanboard.comment.controller;

import com.example.hybrid_kanbanboard.comment.dto.CommentListResponseDto;
import com.example.hybrid_kanbanboard.comment.dto.CommentRequestDto;
import com.example.hybrid_kanbanboard.comment.dto.CommentResponseDto;
import com.example.hybrid_kanbanboard.comment.service.CommentService;
import com.example.hybrid_kanbanboard.security.jwt.UserDetailsImpl;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{cardId}/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<MsgResponseDto> createComment(@PathVariable Long cardId , @AuthenticationPrincipal UserDetailsImpl userDetails , @RequestBody CommentRequestDto commentRequestDto) {
        commentService.createComment(cardId,userDetails.getUser(),commentRequestDto);
        return ResponseEntity.ok().body(new MsgResponseDto("댓글 생성 성공 !", HttpStatus.OK.value()));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long commentId) {
        CommentResponseDto commentResponseDto=commentService.getComment(commentId);
        return ResponseEntity.ok().body(commentResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<CommentListResponseDto> getComments() {
        CommentListResponseDto commentListResponseDto = commentService.getComments();
        return ResponseEntity.ok().body(commentListResponseDto);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<MsgResponseDto> updateComment(@PathVariable Long commentId , @AuthenticationPrincipal UserDetailsImpl userDetails , @RequestBody CommentRequestDto commentRequestDto) {
        commentService.updateComment(commentId,userDetails.getUser(),commentRequestDto);
        return ResponseEntity.ok().body(new MsgResponseDto("댓글 수정 성공 !", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MsgResponseDto> deleteComment(@PathVariable Long commentId , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId,userDetails.getUser());
        return ResponseEntity.ok().body(new MsgResponseDto("댓글 삭제 성공 !", HttpStatus.OK.value()));
    }
}
