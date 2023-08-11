package com.example.hybrid_kanbanboard.checkList.controller;

import com.example.hybrid_kanbanboard.check.dto.CheckResponseDtos;
import com.example.hybrid_kanbanboard.check.dto.CheckRequestDto;
import com.example.hybrid_kanbanboard.checkList.dto.CheckListRequestDto;
import com.example.hybrid_kanbanboard.checkList.dto.CheckListResponseDto;
import com.example.hybrid_kanbanboard.checkList.dto.CheckListResponseDtos;
import com.example.hybrid_kanbanboard.checkList.service.CheckListService;
import com.example.hybrid_kanbanboard.security.jwt.UserDetailsImpl;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/checkList")
public class CheckListController {

    private final CheckListService checkListService;
    @PostMapping("/{checkId}")
    public ResponseEntity<MsgResponseDto> createCheckList(@PathVariable Long checkId , @AuthenticationPrincipal UserDetailsImpl userDetails , @RequestBody CheckListRequestDto checkListRequestDto) {
        checkListService.createCheckList(checkId,userDetails.getUser(), checkListRequestDto);
        return ResponseEntity.ok().body(new MsgResponseDto("체크리스트 생성 성공 !", HttpStatus.OK.value()));
    }

    @GetMapping("/{checkListId}")
    public ResponseEntity<CheckListResponseDto> getComment(@PathVariable Long checkListId) {
        CheckListResponseDto checkListResponseDto = checkListService.getCheckList(checkListId);
        return ResponseEntity.ok().body(checkListResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<CheckListResponseDtos> getCheckLists() {
        CheckListResponseDtos checkListResponseDtos = checkListService.getCheckLists();
        return ResponseEntity.ok().body(checkListResponseDtos);
    }

    @PutMapping("/{checkListId}")
    public ResponseEntity<MsgResponseDto> updateCheckList(@PathVariable Long checkListId , @AuthenticationPrincipal UserDetailsImpl userDetails , @RequestBody CheckListRequestDto checkListRequestDto) {
        checkListService.updateCheckList(checkListId,userDetails.getUser(), checkListRequestDto);
        return ResponseEntity.ok().body(new MsgResponseDto("체크리스트 수정 성공 !", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{checkListId}")
    public ResponseEntity<MsgResponseDto> deleteComment(@PathVariable Long checkListId , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkListService.deleteCheckList(checkListId,userDetails.getUser());
        return ResponseEntity.ok().body(new MsgResponseDto("체크리스트 삭제 성공 !", HttpStatus.OK.value()));
    }

    @PutMapping("/{checkListId}/complated")
    public ResponseEntity<MsgResponseDto> completed(@PathVariable Long checkListId) {
        checkListService.completed(checkListId);
        return ResponseEntity.ok().body(new MsgResponseDto("체크리스트를 완료로 변경했습니다.", HttpStatus.OK.value()));
    }

    @PutMapping("/{checkListId}/notexcuted")
    public ResponseEntity<MsgResponseDto> notExecuted(@PathVariable Long checkListId) {
        checkListService.notExecuted(checkListId);
        return ResponseEntity.ok().body(new MsgResponseDto("체크리스트를 미실행으로 변경했습니다.", HttpStatus.OK.value()));
    }
}
