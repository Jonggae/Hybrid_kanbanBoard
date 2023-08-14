package com.example.hybrid_kanbanboard.check.dto;

import com.example.hybrid_kanbanboard.check.entity.Check;
import com.example.hybrid_kanbanboard.checkList.dto.CheckListResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CheckResponseDto {
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CheckListResponseDto> checkListList;

    public CheckResponseDto(Check check) {
        this.title = check.getTitle();
        this.createdAt = check.getCreatedAt();
        this.modifiedAt = check.getModifiedAt();
        this.checkListList = check.getCheckLists()
                .stream().map(CheckListResponseDto::new).toList();
    }
}
