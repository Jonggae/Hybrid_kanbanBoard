package com.example.hybrid_kanbanboard.user.service;

public interface EmailService {
    void sendEmail(String email) throws Exception;

    boolean verificationCodeCheck(String email, String verificationCode);
}
