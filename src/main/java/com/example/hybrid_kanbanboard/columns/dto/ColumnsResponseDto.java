package com.example.hybrid_kanbanboard.columns.dto;

import com.example.hybrid_kanbanboard.card.dto.CardResponseDto;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ColumnsResponseDto {

    private Long BoardId;
    private Long ColumnId;
    private String columnName;
    private String columnMaker;
    private List<CardResponseDto> cardResponseDtos;

    public ColumnsResponseDto(Columns column) {
        this.ColumnId = column.getColumnId();
        this.BoardId = column.getBoard().getBoardId();
        this.columnMaker = column.getUser().getUserName();
        this.columnName = column.getColumnName();
        this.cardResponseDtos = column.getCards()
                .stream().map(CardResponseDto::new).collect(Collectors.toList());
    }


}
