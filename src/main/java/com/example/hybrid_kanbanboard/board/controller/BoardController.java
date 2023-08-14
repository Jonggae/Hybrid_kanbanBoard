package com.example.hybrid_kanbanboard.board.controller;

import com.example.hybrid_kanbanboard.board.dto.BoardRequestDto;
import com.example.hybrid_kanbanboard.board.dto.BoardResponseDto;
import com.example.hybrid_kanbanboard.board.entity.Board;
import com.example.hybrid_kanbanboard.board.service.BoardService;
import com.example.hybrid_kanbanboard.security.jwt.UserDetailsImpl;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import com.example.hybrid_kanbanboard.user.entity.User;
import com.example.hybrid_kanbanboard.user.service.UserService;
import com.example.hybrid_kanbanboard.userBoard.CollaboratorRequestDto;
import com.example.hybrid_kanbanboard.userBoard.UserBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hybrid")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    //보드 생성
    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    //전체 보드 조회
    @GetMapping("/board")
    public List<BoardResponseDto> getBoard() {
        return boardService.getBoard();
    }

    //보드 + 칼럼 조회
    @GetMapping("/board/{BoardId}")
    public BoardResponseDto getBoardAndCol(@PathVariable Long BoardId) {
        BoardResponseDto responseDto = boardService.getBoardCol(BoardId);
        return responseDto;
    }

    //특정 보드 선택 조회
//    @GetMapping("/board/{BoardId}")
//    public Board getBoardByNum(@PathVariable Long BoardId) {
//        Board responseDto = boardService.findBoard(BoardId);
//        return responseDto;
//    }

    //보드 수정
    @PutMapping("/board/{BoardId}")
    public ResponseEntity<MsgResponseDto> updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long BoardId, @RequestBody BoardRequestDto requestDto) {
        try {
            BoardResponseDto result = boardService.updateBoard(BoardId, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new MsgResponseDto("수정이 완료되었습니다", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("보드 작성자만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    //보드 삭제
    @DeleteMapping("/board/{BoardId}")
    public ResponseEntity<MsgResponseDto> deleteBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long BoardId) {
        try {
            boardService.deleteBoard(BoardId, userDetails.getUser());
            return ResponseEntity.ok().body(new MsgResponseDto("보드가 삭제되었습니다", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("보드 작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    //보드에 사용자 초대
    @PostMapping("/board/collaborator/{BoardId}")
    public ResponseEntity<MsgResponseDto> addCollaborator(@PathVariable Long BoardId, @RequestBody CollaboratorRequestDto collaboratorRequestDto) {
        Board board = boardService.findBoard(BoardId);

        try {
            User collaborator = userService.findById(collaboratorRequestDto.getId());
            UserBoard boardUser = boardService.findCollaborator(collaborator.getUserId());
            boardService.addCollaborator(board, collaborator);

            if (collaborator.equals(board.getUser())) {
                return ResponseEntity.badRequest().body(new MsgResponseDto("입력하신 유저는 보드의 오너입니다.", HttpStatus.BAD_REQUEST.value()));

            }
            if (boardUser.getCollaborator().getUserId().equals(collaborator.getUserId())) {

                return ResponseEntity.badRequest().body(new MsgResponseDto("해당 board에 이미 등록된 유저입니다.", HttpStatus.BAD_REQUEST.value()));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));

        }
        return ResponseEntity.ok().body(new MsgResponseDto("멤버가 등록 되었습니다", HttpStatus.OK.value()));
    }
}
