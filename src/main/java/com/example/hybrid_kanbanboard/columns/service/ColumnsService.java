package com.example.hybrid_kanbanboard.columns.service;

import com.example.hybrid_kanbanboard.board.entity.Board;
import com.example.hybrid_kanbanboard.board.service.BoardService;
import com.example.hybrid_kanbanboard.columns.dto.ColumnsRequestDto;
import com.example.hybrid_kanbanboard.columns.dto.ColumnsResponseDto;
import com.example.hybrid_kanbanboard.columns.entity.Columns;
import com.example.hybrid_kanbanboard.columns.repository.ColumnsRepository;
import com.example.hybrid_kanbanboard.user.dto.UserRoleEnum;
import com.example.hybrid_kanbanboard.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class ColumnsService {

    private final ColumnsRepository columnsRepository;
    private final BoardService boardService;

    // 칼럼 생성
    @Transactional
    public ColumnsResponseDto makeColumn(ColumnsRequestDto requestDto, User user, Long BoardId) {
        Board board = boardService.findBoard(BoardId);
        Columns column = columnsRepository.save(new Columns(requestDto, user, board));

        board.addColumnList(column);
        List<Columns> colList = board.getColumnList();
        for (Columns c : colList) {
            System.out.println("Column = " + c.getColumnName());
        }
        return new ColumnsResponseDto(column);
    }

    // 칼럼 삭제
    @Transactional
    public void deleteColumn(Long ColumnId, Long BoardId, User user) {
        String username = findColumns(ColumnId).getUser().getUserName();
        Board board = boardService.findBoard(BoardId);

        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || username.equals(user.getUserName()))) {
            throw new RejectedExecutionException();
        } else
            columnsRepository.deleteById(ColumnId);
        List<Columns> columnList = board.getColumnList();
        columnList.removeIf(c -> c.getColumnId() == ColumnId); // equal 을 써야하나?

        for (Columns c : columnList) {
            System.out.println("Column = " + c.getColumnId());
        }
    }

    // 칼럼 수정
    @Transactional
    public ColumnsResponseDto updateColumn(Long ColumnId, Long BoardId, ColumnsRequestDto requestDto, User user) {
        String username = findColumns(ColumnId).getUser().getUserName();
        Board board = boardService.findBoard(BoardId);
        Columns column = findColumns(ColumnId);

        if (username.equals(user.getUserName())|| user.getRole().toString().equals("ADMIN")) {
            List<Columns> columnList = board.getColumnList();
            column.update(requestDto);
            return new ColumnsResponseDto(column);
        } else {
            System.out.println("수정 권한이 없습니다");
            return null;
        }

    }

    // 칼럼 검색
    public Columns findColumns(Long ColumnsId) {
        return columnsRepository.findById(ColumnsId).orElseThrow(() ->
                new IllegalArgumentException("해당 칼럼이 존재하지 않습니다."));
    }
}
