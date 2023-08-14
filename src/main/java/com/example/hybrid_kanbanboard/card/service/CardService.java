package com.example.hybrid_kanbanboard.card.service;

import com.example.hybrid_kanbanboard.card.dto.*;
import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.card.repository.CardRepository;
import com.example.hybrid_kanbanboard.cardUser.entity.CardUser;
import com.example.hybrid_kanbanboard.cardUser.repository.CardUserRepository;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import com.example.hybrid_kanbanboard.columns.service.ColumnsService;
import com.example.hybrid_kanbanboard.notification.utility.NewCardMovedEvent;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import com.example.hybrid_kanbanboard.upload.sevice.S3UploadService;
import com.example.hybrid_kanbanboard.user.dto.UserRoleEnum;
import com.example.hybrid_kanbanboard.user.entity.User;
import com.example.hybrid_kanbanboard.user.repository.UserRepository;
import com.example.hybrid_kanbanboard.user.service.UserService;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;
    private final CardUserRepository cardUserRepository;
    private final UserRepository userRepository;
    private final UserService userService;

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

    // 카드 이동
    public void reorderCard(Long cardId, Long columnsId, CardReorderRequestDto reorderRequestDto) {
        Card card = findCard(cardId);

        Columns columns = card.getColumns();
        Long oldPosition = card.getPosition();
        Long newPosition = reorderRequestDto.getPosition();

        if (columns.getColumnId().equals(columnsId)) {

            if (newPosition > oldPosition) {
                cardRepository.decrementAboveToPosition(newPosition, oldPosition,
                        String.valueOf(columns.getColumnId()));
            } else {
                cardRepository.incrementBelowToPosition(newPosition, oldPosition,
                        String.valueOf(columns.getColumnId()));
            }

            card.setPosition(reorderRequestDto.getPosition());
            cardRepository.save(card);

        } else {

            Columns requestcolumns = columnsService.findColumns(columnsId);

            cardRepository.decrementBelow(card.getPosition(), String.valueOf(columns.getColumnId()));

            Long position = cardRepository.countCardsByColumns(requestcolumns);
            card.setPosition(position);
            card.setColumns(requestcolumns);

            cardRepository.save(card);

            // 이 부분에 이벤트 발행 코드 추가
            eventPublisher.publishEvent(new NewCardMovedEvent(this, card, columns, requestcolumns));
        }
    }

    @Transactional
    public void addMember(String userName, Long cardId) {
        User user = findUserName(userName);
        Card card = findCard(cardId);
        CardUser cardUser = cardUserRepository.findByUserAndCard(user, card).orElse(null);

        if (cardUser == null) {
            cardUser = new CardUser(user, card);
            cardUserRepository.save(cardUser);
        } else {
            cardUser.cancel();
            cardUserRepository.delete(cardUser);
        }
    }

    public Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
    public User findUserName(String userName) {
        return cardRepository.findByUser_userName(userName);
    }


}
