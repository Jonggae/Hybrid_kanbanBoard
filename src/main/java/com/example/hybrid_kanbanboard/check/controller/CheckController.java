package com.example.hybrid_kanbanboard.check.controller;

import com.example.hybrid_kanbanboard.check.dto.CheckRequestDto;
import com.example.hybrid_kanbanboard.check.dto.CheckResponseDto;
import com.example.hybrid_kanbanboard.check.dto.CheckResponseDtos;
import com.example.hybrid_kanbanboard.check.service.CheckService;
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
@RequestMapping("/check")
public class CheckController {

    private final CheckService checkService;

    @PostMapping("/{cardId}")
    public ResponseEntity<MsgResponseDto> createCheckList(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CheckRequestDto checkRequestDto) {
        checkService.createCheck(cardId, userDetails.getUser(), checkRequestDto);
        return ResponseEntity.ok().body(new MsgResponseDto("체크보드 생성 성공 !", HttpStatus.OK.value()));
    }

    @GetMapping("/{checkId}")
    public ResponseEntity<CheckResponseDto> getComment(@PathVariable Long checkId) {
        CheckResponseDto checkResponseDto = checkService.getCheck(checkId);
        return ResponseEntity.ok().body(checkResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<CheckResponseDtos> getCheckLists() {
        CheckResponseDtos checkResponses = checkService.getChecks();
        return ResponseEntity.ok().body(checkResponses);
    }

    @PutMapping("/{checkId}")
    public ResponseEntity<MsgResponseDto> updateCheckList(@PathVariable Long checkId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CheckRequestDto checkRequestDto) {
        checkService.updateCheck(checkId, userDetails.getUser(), checkRequestDto);
        return ResponseEntity.ok().body(new MsgResponseDto("체크보드 수정 성공 !", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{checkId}")
    public ResponseEntity<MsgResponseDto> deleteComment(@PathVariable Long checkId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkService.deleteCheck(checkId, userDetails.getUser());
        return ResponseEntity.ok().body(new MsgResponseDto("체크보드 삭제 성공 !", HttpStatus.OK.value()));
    }
}
