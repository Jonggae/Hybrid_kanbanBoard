package com.example.hybrid_kanbanboard.check.service;

import com.example.hybrid_kanbanboard.card.entity.Card;
import com.example.hybrid_kanbanboard.card.service.CardService;
import com.example.hybrid_kanbanboard.check.dto.CheckRequestDto;
import com.example.hybrid_kanbanboard.check.dto.CheckResponseDto;
import com.example.hybrid_kanbanboard.check.dto.CheckResponseDtos;
import com.example.hybrid_kanbanboard.check.entity.Check;
import com.example.hybrid_kanbanboard.check.repository.CheckRepository;
import com.example.hybrid_kanbanboard.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckService {

    private final CheckRepository checkRepository;
    private final CardService cardService;

    @Transactional
    public void createCheck(Long cardId, User user, CheckRequestDto checkRequestDto) {
        Card card = cardService.findCard(cardId);
        Check check = new Check(checkRequestDto, card, user);
        card.addCheck(check);

        checkRepository.save(check);
    }

    @Transactional
    public CheckResponseDto getCheck(Long checkId) {
        Check check = findCheck(checkId);

        return new CheckResponseDto(check);
    }

    @Transactional
    public CheckResponseDtos getChecks() {
        List<CheckResponseDto> checkResponseDto = checkRepository.findAllByOrderByModifiedAtDesc()
                .stream().map(CheckResponseDto::new).collect(Collectors.toList());
        return new CheckResponseDtos(checkResponseDto);
    }

    @Transactional
    public void updateCheck(Long checkId, User user, CheckRequestDto checkRequestDto) {
        Check check = findCheck(checkId);

        if (!check.getUser().getUserId().equals(user.getUserId())) {
            // 예외처리
        }
        check.updateCheckList(checkRequestDto);
    }

    @Transactional
    public void deleteCheck(Long checkId, User user) {
        Check check = findCheck(checkId);
        if (!check.getUser().getUserId().equals(user.getUserId())) {
            // 예외처리
        }
        checkRepository.delete(check);
    }

    public Check findCheck(Long checkId) {
        return checkRepository.findById(checkId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
    }
}
