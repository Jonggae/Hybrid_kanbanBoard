package com.example.hybrid_kanbanboard.user.service;

import com.example.hybrid_kanbanboard.security.jwt.RedisUtil;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private String verificationCode = makeRandomCode();
    private final RedisUtil redisUtil; //redis 관련

//    @Qualifier("javaMailSender")
    private final JavaMailSender emailSender;

    // 이메일 전송 메서드
    @Override
    public void sendEmail(String email) throws Exception {

        MimeMessage message = joinEmail(email); // 인증 코드를 joinEmail 메서드로 전달
        if (redisUtil.existData(email)) {
            redisUtil.deleteData(email);
        }
        try {
            emailSender.send(message); // MimeMessage joinEmail의 message
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        redisUtil.setDataExpire(email, verificationCode, 60 * 5L);
    }

    // 이메일 인증 코드 검증
    @Override
    public boolean verificationCodeCheck(String email, String verificationCode) {
        String codeFindByEmail = redisUtil.getData(email);
        if (codeFindByEmail == null) {
            return false;
        }
        redisUtil.save("success", email, 60 * 50L);
        return redisUtil.getData(email).equals(verificationCode);
    }

    // 이메일 보낼 양식
    public MimeMessage joinEmail(String email) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email); // 보내는 대상
        message.setSubject("Hybrid 이메일 인증 코드"); // 이메일 제목

        String mailMsg="";
        mailMsg+="<div>";
        mailMsg+="Hybrid 회원가입 관련 이메일 인증 코드입니다";
        mailMsg+="<br>";
        mailMsg+="CODE : <strong>";
        mailMsg+=verificationCode;
        mailMsg+="</strong></div>";

        message.setText(mailMsg, "utf-8", "html");
        message.setFrom(new InternetAddress("heehee010010@gmail.com", "Hybrid-KimJinHee")); // setFrom 안 됐었는데...? 일단 보내는 사람 적용

        return message;
    }

    public String makeRandomCode() {
        StringBuffer key = new StringBuffer();
        Random random = new Random();
        for (int i=0; i<6; i++) { // 6 자리 인증번호, 알파벳 섞이지 않게 한 개씩
            int index = random.nextInt(10); // 0~9까지 랜덤
            key.append(index);
        }
        return key.toString();
    }
}
