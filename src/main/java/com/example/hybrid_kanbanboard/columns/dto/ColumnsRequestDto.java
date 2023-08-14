package com.example.hybrid_kanbanboard.columns.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnsRequestDto {
    private String columnName;
    private Long columnPosition;
}
