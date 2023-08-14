package com.example.hybrid_kanbanboard.user.dto;

import com.example.hybrid_kanbanboard.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileUpdateDto {
    private String email;
    private String password;
    private String changePassword;
    private String nickname;
    private String myContent;

    public ProfileUpdateDto(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.myContent = user.getMyContent();
    }
}
