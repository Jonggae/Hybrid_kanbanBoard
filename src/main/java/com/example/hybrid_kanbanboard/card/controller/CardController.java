package com.example.hybrid_kanbanboard.card.controller;

import com.example.hybrid_kanbanboard.card.dto.*;
import com.example.hybrid_kanbanboard.card.service.CardService;
import com.example.hybrid_kanbanboard.security.jwt.UserDetailsImpl;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import lombok.RequiredArgsConstructor;
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
public class CardController {

    private final CardService cardService;

    @PostMapping(value = "/{columnId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MsgResponseDto> createCard(@PathVariable Long columnId ,@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @RequestPart CardRequestDto requestDto , @RequestPart MultipartFile multipartFile) throws IOException {
        cardService.createCard(requestDto, userDetails.getUser(),multipartFile,columnId);

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


    // 카드 이동
    @PutMapping("/cards/{cardId}/reorder")
    public ResponseEntity<Void> reorderCard(@PathVariable Long cardId,
                                            @RequestParam(required = false) Long columnsId,
                                            @RequestBody CardReorderRequestDto reorderRequestDto) {
        cardService.reorderCard(cardId, columnsId, reorderRequestDto);
        return ResponseEntity.ok().build();
    }



}
