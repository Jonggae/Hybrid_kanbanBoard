package com.example.hybrid_kanbanboard.user.controller;

import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import com.example.hybrid_kanbanboard.user.dto.SignUpRequestDto;
import com.example.hybrid_kanbanboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hybrid")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MsgResponseDto> signup(@RequestBody SignUpRequestDto requestDto){
            userService.signup(requestDto);
            return ResponseEntity.ok().body(new MsgResponseDto("회원가입 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/signupboard")
    public ResponseEntity<MsgResponseDto> signupBoard(@RequestBody SignUpRequestDto requestDto) {
        userService.signupBoard(requestDto);
        return ResponseEntity.ok().body(new MsgResponseDto("보드 가입 성공",HttpStatus.OK.value()));
    }
}
