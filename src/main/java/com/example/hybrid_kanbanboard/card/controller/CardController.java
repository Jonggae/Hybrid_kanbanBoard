package com.example.hybrid_kanbanboard.card.controller;

import com.example.hybrid_kanbanboard.card.dto.*;
import com.example.hybrid_kanbanboard.card.service.CardService;
import com.example.hybrid_kanbanboard.security.jwt.UserDetailsImpl;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import com.example.hybrid_kanbanboard.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

// ("/api/column")

    // 카드 생성
    @PostMapping("/columns/{columnsId}/cards")
    public ResponseEntity<MsgResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long columnsId,
                                                     @RequestBody CardRequestDto requestDto) {
        cardService.createCard(columnsId, requestDto, userDetails.getUser());

        return ResponseEntity.ok().body(new MsgResponseDto("카드 생성 성공!", HttpStatus.OK.value()));
    }


    // 카드 이름 수정
    @PutMapping("cards/{cardId}/name")
    public ResponseEntity<MsgResponseDto> updateName(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long cardId,
                                                     @RequestBody NameRequestDto requestDto) {
        cardService.updateName(cardId, requestDto, userDetails.getUser());

        return ResponseEntity.ok().body(new MsgResponseDto("카드 생성 성공!", HttpStatus.OK.value()));

    }

    // 카드 설명 수정
    @PutMapping("cards/{cardId}/description")
    public ResponseEntity<MsgResponseDto> updateDescription(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable Long cardId,
                                                            @RequestBody DescriptionRequestDto requestDto) {
        cardService.updateDescription(cardId, requestDto, userDetails.getUser());

        return ResponseEntity.ok().body(new MsgResponseDto("카드 제목 수정 성공!", HttpStatus.OK.value()));
    }
    // 카드 색상 수정
    @PutMapping("cards/{cardId}/color")
    public ResponseEntity<MsgResponseDto> updateColor(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable Long cardId,
                                                      @RequestBody ColorRequestDto requestDto) {
        cardService.updateColor(cardId, requestDto, userDetails.getUser());

        return ResponseEntity.ok().body(new MsgResponseDto("카드 생성 성공!", HttpStatus.OK.value()));
    }
    // 카드 만기일 수정
    @PutMapping("cards/{cardId}/due")
    public ResponseEntity<MsgResponseDto> updateDueDate(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long cardId,
                                                        @RequestBody DueDateRequestDto requestDto) {
        cardService.updateDueDate(cardId, requestDto, userDetails.getUser());

        return ResponseEntity.ok().body(new MsgResponseDto("카드 생성 성공!", HttpStatus.OK.value()));

    }


    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<MsgResponseDto> deleteCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long cardId) {
        try {
            cardService.deleteCard(cardId, userDetails.getUser());
            return ResponseEntity.ok().body(new MsgResponseDto("카드 삭제 성공,", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }









}
