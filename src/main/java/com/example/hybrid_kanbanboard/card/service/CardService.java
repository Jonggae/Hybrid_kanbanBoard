package com.example.hybrid_kanbanboard.card.service;

import com.example.hybrid_kanbanboard.board.service.BoardService;
import com.example.hybrid_kanbanboard.card.dto.*;
import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.card.repository.CardRepository;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import com.example.hybrid_kanbanboard.columns.repository.ColumnsRepository;
import com.example.hybrid_kanbanboard.columns.service.ColumnsService;
import com.example.hybrid_kanbanboard.user.dto.UserRoleEnum;
import com.example.hybrid_kanbanboard.user.entity.User;
import com.example.hybrid_kanbanboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final ColumnsService columnsService;





    @Transactional
    public void createCard(Long columnId, CardRequestDto requestDto, User user) {
        Columns columns = columnsService.findColumns(columnId);

        Card card = new Card(columns, requestDto, user);

        cardRepository.save(card);
    }


    // 삭제할 예정
    @Transactional
    public CardResponseDto updateCard(Long cardId, CardRequestDto requestDto, User user) {
        Card card = findCard(cardId);

        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || card.getUser().getUserId().equals(user.getUserId()))) {
            throw new RejectedExecutionException();
        }

        return new CardResponseDto(card.update(requestDto));
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





















    private Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
