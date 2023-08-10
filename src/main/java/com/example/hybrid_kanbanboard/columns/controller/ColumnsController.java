package com.example.hybrid_kanbanboard.columns.controller;

import com.example.hybrid_kanbanboard.columns.dto.ColumnsRequestDto;
import com.example.hybrid_kanbanboard.columns.dto.ColumnsResponseDto;
import com.example.hybrid_kanbanboard.columns.service.ColumnsService;
import com.example.hybrid_kanbanboard.security.jwt.UserDetailsImpl;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping(value ="/hybrid")
@RequiredArgsConstructor
public class ColumnsController {

    private final ColumnsService columnsService;

    //칼럼 생성
    @PostMapping(value = "/column")
    public ColumnsResponseDto makeColumn(@RequestBody ColumnsRequestDto requestDto, @RequestParam Long BoardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return columnsService.makeColumn(requestDto, userDetails.getUser(), BoardId);
    }

    //칼럼 삭제

    @DeleteMapping("/column/{ColumnId}")
    public ResponseEntity<MsgResponseDto> deleteColumn(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long ColumnId, @RequestParam Long BoardId) {
        try {
        columnsService.deleteColumn(ColumnId,BoardId,userDetails.getUser());
        return ResponseEntity.ok().body(new MsgResponseDto("칼럼이 삭제되었습니다.", HttpStatus.OK.value()));
        }catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 칼럼 수정

    @PutMapping("/column/{ColumnId}")
    public ColumnsResponseDto updateColumn(@PathVariable Long ColumnId, @RequestParam Long BoardId, @RequestBody ColumnsRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return columnsService.updateColumn(ColumnId,BoardId,requestDto,userDetails.getUser());
    }
}
