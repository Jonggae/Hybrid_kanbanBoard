package com.example.hybrid_kanbanboard.card.service;

import com.example.hybrid_kanbanboard.card.dto.*;
import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.card.repository.CardRepository;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import com.example.hybrid_kanbanboard.columns.service.ColumnsService;
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
    private final ColumnsService columnsService;




    @Transactional
    public void createCard(CardRequestDto requestDto, User user, MultipartFile multipartFile,Long columnId) throws IOException {
        Columns columns = columnsService.findColumns(columnId);

        s3UploadService.saveFile(multipartFile);
        log.info("이름 : {} , 나이 : {} , 이미지 : {}",requestDto.getName(),requestDto.getDescription(),multipartFile);

        Card card = new Card(requestDto, user);
        columns.addCards(card);

        cardRepository.save(card);
    }


    public void deleteCard(Long cardId, User user) {
        Card card = findCard(cardId);

        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || card.getUser().getUserId().equals(user.getUserId()))) {
            throw new RejectedExecutionException();
        }

        cardRepository.delete(card);
    }


    // 제목 수정
    @Transactional
    public void updateName(Long cardId, NameRequestDto requestDto, User user) {
        Card card = findCard(cardId);

        card.updateName(requestDto);
    }

    // 설명 수정
    @Transactional
    public void updateDescription(Long cardId, DescriptionRequestDto requestDto, User user) {
        Card card = findCard(cardId);

        card.updateDescription(requestDto);
    }

    // 색상 수정
    @Transactional
    public void updateColor(Long cardId, ColorRequestDto requestDto, User user) {
        Card card = findCard(cardId);

        card.updateColor(requestDto);
    }

    // 만기일 수정
    @Transactional
    public void updateDueDate(Long cardId, DueDateRequestDto requestDto, User user) {
        Card card = findCard(cardId);

        card.updateDueDate(requestDto);
    }


    public Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
