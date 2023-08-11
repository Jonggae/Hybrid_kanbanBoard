package com.example.hybrid_kanbanboard.card.controller;

import com.example.hybrid_kanbanboard.card.dto.CardRequestDto;
import com.example.hybrid_kanbanboard.card.dto.CardResponseDto;
import com.example.hybrid_kanbanboard.card.service.CardService;
import com.example.hybrid_kanbanboard.security.jwt.UserDetailsImpl;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
@Slf4j
public class CardController {

    private final CardService cardService;

    @PostMapping(value = "/v1/character", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MsgResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @RequestPart CardRequestDto requestDto ,@RequestPart MultipartFile multipartFile) throws IOException {
        cardService.createCard(requestDto, userDetails.getUser(),multipartFile);

        return ResponseEntity.ok().body(new MsgResponseDto("카드 생성 성공!", HttpStatus.OK.value()));
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<MsgResponseDto> updateCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long cardId,
                                                     @RequestBody CardRequestDto requestDto) {
        try {
            CardResponseDto result = cardService.updateCard(cardId, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }


    @DeleteMapping("/{cardId}")
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
