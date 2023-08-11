package com.example.hybrid_kanbanboard.card.controller;

import com.example.hybrid_kanbanboard.card.dto.CardRequestDto;
import com.example.hybrid_kanbanboard.card.dto.CardResponseDto;
import com.example.hybrid_kanbanboard.card.service.CardService;
import com.example.hybrid_kanbanboard.security.jwt.UserDetailsImpl;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping(value = "/v1/character", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MsgResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @RequestPart CardRequestDto requestDto ,@RequestPart MultipartFile multipartFile) throws IOException {
        cardService.createCard(requestDto, userDetails.getUser(),multipartFile);

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
