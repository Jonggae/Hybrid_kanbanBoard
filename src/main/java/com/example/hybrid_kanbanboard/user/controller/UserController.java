package com.example.hybrid_kanbanboard.user.controller;

import com.example.hybrid_kanbanboard.security.jwt.UserDetailsImpl;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import com.example.hybrid_kanbanboard.user.dto.EmailVerificationRequestDto;
import com.example.hybrid_kanbanboard.user.dto.ProfileUpdateDto;
import com.example.hybrid_kanbanboard.user.dto.SignUpRequestDto;
import com.example.hybrid_kanbanboard.user.dto.UserResponseDto;
import com.example.hybrid_kanbanboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hybrid")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MsgResponseDto> signup(@RequestBody SignUpRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok().body(new MsgResponseDto("회원가입 성공", HttpStatus.OK.value()));
    }

    @GetMapping("/profile")
    public UserResponseDto detailProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.detailProfile(userDetails);
    }

    @PutMapping("/profile")
    public ResponseEntity<MsgResponseDto> profileUpdateDto(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                           @RequestBody ProfileUpdateDto profileUpdateDto
    ) throws Exception {

        userService.updateProfile(profileUpdateDto, userDetails);

        return ResponseEntity.ok().body(new MsgResponseDto("프로필 수정 성공", HttpStatus.CREATED.value()));
    }
}
