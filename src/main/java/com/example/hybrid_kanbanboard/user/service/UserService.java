package com.example.hybrid_kanbanboard.user.service;

import com.example.hybrid_kanbanboard.user.dto.SignUpRequestDto;
import com.example.hybrid_kanbanboard.user.dto.UserRoleEnum;
import com.example.hybrid_kanbanboard.user.entity.User;
import com.example.hybrid_kanbanboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignUpRequestDto requestDto) {
        String userName = requestDto.getUserName();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        UserRoleEnum role = requestDto.getRole();

        // 회원 중복 확인 --> Entity 에서도  @Column(nullable = false, unique = true) 이런식으로 고유값으로 선언해야한다
        Optional<User> checkUsername = userRepository.findByUserName(userName);
        if (checkUsername.isPresent()) { //isPresent() --> 데이터에 동일 데이터가 있는지 확인.
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        Optional<User> checkUserEmail = userRepository.findByEmail(email);
        if (checkUserEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 등록
        User user = new User(userName, password, email, nickname, role);
        userRepository.save(user);
    }
    // 보드에 사용자 초대하기 * 보드 생성자만 초대 가능하다...
    public void signupBoard(SignUpRequestDto requestDto) {
        String userName = requestDto.getUserName();
        User user = new User(userName);


    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    }
}
