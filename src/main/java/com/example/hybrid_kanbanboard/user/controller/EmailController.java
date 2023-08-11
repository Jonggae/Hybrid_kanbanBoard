package com.example.hybrid_kanbanboard.user.controller;

import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import com.example.hybrid_kanbanboard.user.dto.EmailVerificationRequestDto;
import com.example.hybrid_kanbanboard.user.service.EmailService;
import com.example.hybrid_kanbanboard.user.service.EmailServiceImpl;
import com.example.hybrid_kanbanboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController {

    private final EmailServiceImpl emailService;
    // 인증 코드를 Email로 보내기
    @PostMapping("/email")
    public ResponseEntity<MsgResponseDto> sendEmail(@RequestParam("email") String emails) throws Exception {
        log.info("email: " + emails);
        emailService.sendEmail(emails);
        return ResponseEntity.ok().body(new MsgResponseDto("이메일 인증 코드 전송 완료", HttpStatus.OK.value()));
    }

    // 받은 인증 코드를 입력하고 검증하기
    @PostMapping("/email-check") //URL 대문자가 들어가면 안됩니다
    public ResponseEntity<MsgResponseDto> checkEmail(@RequestBody EmailVerificationRequestDto emailVerificationRequestDto) throws Exception {
        // email 인증 코드 확인
        String enterEmail = emailVerificationRequestDto.getEmail();
        String enterVerificationCode = emailVerificationRequestDto.getVerificationCode();

        boolean auth = emailService.verificationCodeCheck(enterEmail, enterVerificationCode);
        if (auth) {
            return ResponseEntity.ok().body(new MsgResponseDto("이메일 인증 성공", HttpStatus.OK.value()));
        }
        return ResponseEntity.badRequest().body(new MsgResponseDto("이메일 인증 실패", HttpStatus.BAD_REQUEST.value()));
    }
}

