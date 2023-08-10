package com.example.hybrid_kanbanboard.columns.dto;

import com.example.hybrid_kanbanboard.columns.entity.Columns;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnsResponseDto {

    private Long BoardId;
    private Long ColumnId;
    private String columnName;
    private String columnMaker;

    public ColumnsResponseDto(Columns column) {
        this.ColumnId = column.getColumnId();
        this.BoardId = column.getBoard().getBoardId();
        this.columnMaker = column.getUser().getUserName();
        this.columnName = column.getColumnName();
    }


}
