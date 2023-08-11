package com.example.hybrid_kanbanboard.user.service;

import com.example.hybrid_kanbanboard.security.jwt.RedisUtil;
import com.example.hybrid_kanbanboard.security.jwt.UserDetailsImpl;
import com.example.hybrid_kanbanboard.user.dto.ProfileUpdateDto;
import com.example.hybrid_kanbanboard.user.dto.SignUpRequestDto;
import com.example.hybrid_kanbanboard.user.dto.UserResponseDto;
import com.example.hybrid_kanbanboard.user.dto.UserRoleEnum;
import com.example.hybrid_kanbanboard.user.entity.PasswordHistory;
import com.example.hybrid_kanbanboard.user.entity.User;
import com.example.hybrid_kanbanboard.user.repository.PasswordHistoryRepository;
import com.example.hybrid_kanbanboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final RedisUtil redisUtil; //redis 관련

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

        // key에 맞는 value를 줌
        String verifiedEmail = redisUtil.getEmail("success");
        if (verifiedEmail == null) {
            throw new IllegalArgumentException("인증한 Email이 없습니다");
        }
        // 인증 성공한 이메일과 회원가입 할 때 입력한 이메일을 비교함
        if (!verifiedEmail.equals(email)) {
            throw new IllegalArgumentException("인증되지 않은 Email 입니다.");
        }
        // if문 2개를 정리 할 수 있을까요? 물어보기

        // 사용자 등록
        User user = new User(userName, password, email, nickname, role);
        userRepository.save(user);
    }

    public UserResponseDto detailProfile (UserDetailsImpl userDetails) {
        UserResponseDto userResponseDto = new UserResponseDto(userDetails.getUser());
        return userResponseDto;
    }

    @Transactional
    public ProfileUpdateDto updateProfile (ProfileUpdateDto profileUpdateDto, UserDetailsImpl userDetails) throws Exception {

        System.out.println(profileUpdateDto.getPassword() + "               " + userDetails.getPassword());

        if (!passwordEncoder.matches(profileUpdateDto.getPassword(), userDetails.getPassword())) {

            throw new IllegalArgumentException("비밀번호 인증에 실패했습니다.");
        }
        // 프로필 수정 시 비밀번호를 한 번 더 입력받는 과정

        List<PasswordHistory> passwordHistories = passwordHistoryRepository.findTop4ByUserOrderByCreatedAtDesc(userDetails.getUser());
        // 최근 3번 사용한 비밀번호 조회

        for (int i = 0; i <passwordHistories.size(); i++) {
            PasswordHistory passwordHistory = passwordHistories.get(i);
            // passwordHistories.get(i)를 통해 passwordHistories 리스트에서 i번째 인덱스에 위치한 PasswordHistory 객체를 가져옴
            if (passwordEncoder.matches(profileUpdateDto.getChangePassword(), passwordHistory.getPassword()))
                throw new IllegalArgumentException("최근 3번 사용한 비밀번호는 사용할 수 없습니다.");
            // passwordHistory.getUserPassword()(암호화) 된 비밀번호가 userDetails.getPassword()(사용자가 입력한 비밀번호)와 같다면 throw를 보냄
        }

        // 최근 3번 사용한 비밀번호 제한하기

        profileUpdateDto.setChangePassword(passwordEncoder.encode(profileUpdateDto.getChangePassword()));
        // 받아온 비밀번호 암호화 시켜줌

        User targetUser = userRepository.findById(userDetails.getUser().getUserId()).orElseThrow(() -> new Exception ());
        // targetUser는 User클래스의 객체이다
        // userRepository에서 userId로 user를 한 개 찾아온다
        // userId는 현재 로그인 한 Id를 찾아와야 함으로 userDetails를 실행시킨다
        // 반환타입이 Optional이기 때문에 null 값에 대한 처리를 해주기 위해 orElseThrow(() -> new Exception ())으로 null 값인 경우 Exception을 던진다

        // DB에 있는 유저의 정보를 바꾸기 위해 User를 가져오고 userRepository 안에 id를 찾아와라

        System.out.println(profileUpdateDto.getChangePassword());

        targetUser.updateProfile(profileUpdateDto);

        passwordHistoryRepository.save(new PasswordHistory(userDetails.getUser(), profileUpdateDto.getChangePassword()));

        return profileUpdateDto;
    }
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    }
}
