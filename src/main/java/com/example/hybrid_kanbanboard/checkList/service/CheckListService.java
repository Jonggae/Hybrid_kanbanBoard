package com.example.hybrid_kanbanboard.checkList.service;

import com.example.hybrid_kanbanboard.check.entity.Check;
import com.example.hybrid_kanbanboard.check.repository.CheckRepository;
import com.example.hybrid_kanbanboard.checkList.dto.CheckListRequestDto;
import com.example.hybrid_kanbanboard.checkList.dto.CheckListResponseDto;
import com.example.hybrid_kanbanboard.checkList.dto.CheckListResponseDtos;
import com.example.hybrid_kanbanboard.checkList.entity.CheckList;
import com.example.hybrid_kanbanboard.checkList.repository.CheckListRepository;
import com.example.hybrid_kanbanboard.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final CheckRepository checkRepository;

    @Transactional
    public void createCheckList(Long checkId, User user, CheckListRequestDto checkListRequestDto) {
        Check check = checkRepository.findById(checkId).orElseThrow();
        // if() 보드에 추가된사람만 생성 할 수 있게 예외처리.
        CheckList checkList = new CheckList(checkListRequestDto, user, check);
        check.addCheckList(checkList);

        checkListRepository.save(checkList);
    }

    @Transactional
    public CheckListResponseDto getCheckList(Long checkListId) {
        CheckList checkList = findCheckList(checkListId);
        // if() 보드에 추가된사람만 조회 할 수 있게 예외처리.
        return new CheckListResponseDto(checkList);
    }

    @Transactional
    public CheckListResponseDtos getCheckLists() {
        List<CheckListResponseDto> checkListResponseDtos = checkListRepository.findAllByOrderByModifiedAtDesc()
                .stream().map(CheckListResponseDto::new).collect(Collectors.toList());
        return new CheckListResponseDtos(checkListResponseDtos);
    }

    @Transactional
    public void updateCheckList(Long checkListId, User user, CheckListRequestDto checkListRequestDto) {
        CheckList checkList = findCheckList(checkListId);
        if (!checkList.getUser().getUserId().equals(user.getUserId())) {
            //예외처리
        }
        checkList.updateCheckList(checkListRequestDto);
    }

    @Transactional
    public void deleteCheckList(Long checkListId, User user) {
        CheckList checkList = findCheckList(checkListId);
        if (!checkList.getUser().getUserId().equals(user.getUserId())) {
            //예외처리
        }
        checkListRepository.delete(checkList);

    }

    @Transactional
    public void completed(Long checkListId) {
        CheckList checkList = findCheckList(checkListId);
        if (!checkList.isCompleted() == false) {
            //예외처리
        }
        checkList.setIsCompleted();
    }

    @Transactional
    public void notExecuted(Long checkListId) {
        CheckList checkList = findCheckList(checkListId);
        if (!checkList.isCompleted() == true) {
            //예외처리
        }
        checkList.setIsCompleted();
    }

    @Transactional
    public CheckList findCheckList(Long checkListId) {
        return checkListRepository.findById(checkListId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
    }
}
