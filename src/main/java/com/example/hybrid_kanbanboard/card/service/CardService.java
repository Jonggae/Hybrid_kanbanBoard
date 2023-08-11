package com.example.hybrid_kanbanboard.card.service;

import com.example.hybrid_kanbanboard.card.dto.CardRequestDto;
import com.example.hybrid_kanbanboard.card.dto.CardResponseDto;
import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.card.repository.CardRepository;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import com.example.hybrid_kanbanboard.upload.sevice.S3UploadService;
import com.example.hybrid_kanbanboard.user.dto.UserRoleEnum;
import com.example.hybrid_kanbanboard.user.entity.User;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    private final S3UploadService s3UploadService;


    public void createCard(CardRequestDto requestDto, User user, MultipartFile multipartFile) throws IOException {
        Card card = new Card(requestDto,user);

        s3UploadService.saveFile(multipartFile);
        log.info("이름 : {} , 나이 : {} , 이미지 : {}",requestDto.getName(),requestDto.getDescription(),multipartFile);

        cardRepository.save(card);
    }


    @Transactional
    public CardResponseDto updateCard(Long id, CardRequestDto requestDto, User user) {
        Card card = findCard(id);

        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || card.getUser().getUserId().equals(user.getUserId()))) {
            throw new RejectedExecutionException();
        }

        return new CardResponseDto(card.update(requestDto));
    }


    public void deleteCard(Long id, User user) {
        Card card = findCard(id);

        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || card.getUser().getUserId().equals(user.getUserId()))) {
            throw new RejectedExecutionException();
        }

        cardRepository.delete(card);
    }


    public Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
