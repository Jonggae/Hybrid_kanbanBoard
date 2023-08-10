package com.example.hybrid_kanbanboard.board.service;

import com.example.hybrid_kanbanboard.board.dto.BoardRequestDto;
import com.example.hybrid_kanbanboard.board.dto.BoardResponseDto;
import com.example.hybrid_kanbanboard.board.entity.Board;
import com.example.hybrid_kanbanboard.board.repository.BoardRepository;
import com.example.hybrid_kanbanboard.user.dto.UserRoleEnum;
import com.example.hybrid_kanbanboard.user.entity.User;
import com.example.hybrid_kanbanboard.userBoard.UserBoard;
import com.example.hybrid_kanbanboard.userBoard.UserBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;

    // 보드 생성
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        UserRoleEnum role = UserRoleEnum.ADMIN;
        Board board = boardRepository.save(new Board(requestDto, user, role));
        UserBoard userBoard = new UserBoard(user, board);
        userBoardRepository.save(userBoard);
        return new BoardResponseDto(board);
    }

    // 보드 조회
    public List<BoardResponseDto> getBoard() {
        return boardRepository.findAll().stream().map(BoardResponseDto::new).toList();
    }

    //보드 + 칼럼 조회
    public BoardResponseDto getBoardCol(Long BoardId) {
        Board board = boardRepository.findById(BoardId).orElseThrow(() ->
                new IllegalArgumentException("선택한 보드가 존재하지 않습니다."));
        return new BoardResponseDto(board);
    }

    // 특정 보드 조회
    public Board findBoard(Long BoardId) {
        return boardRepository.findById(BoardId).orElseThrow(() ->
                new IllegalArgumentException("선택한 보드가 존재하지 않습니다"));
    }

    // 보드 수정
    // 만든 사람이 수정할 수 있음 (보드 이름이나 설명)
    @Transactional
    public BoardResponseDto updateBoard(Long BoardId, BoardRequestDto requestDto, User user) {
        String username = findBoard(BoardId).getUser().getUserName();
        Board board = findBoard(BoardId);

        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || username.equals(user.getUserName()))) {
            throw new RejectedExecutionException();
        }
        board.setBoardName(requestDto.getBoardName());
        board.setDescription(requestDto.getDescription());

        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(Long BoardId, User user) {
        String username = findBoard(BoardId).getUser().getUserName();
        Board board = findBoard(BoardId);
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || username.equals(user.getUserName()))) {
            throw new RejectedExecutionException();
        } else
            boardRepository.delete(board);
    }

    public List<BoardResponseDto> getAllBoard() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDto> responseDtoList = new ArrayList<>();
        for (Board board : boardList) {
            responseDtoList.add(new BoardResponseDto(board));
        }
        return responseDtoList;
    }

    public void addCollaborator(Board board, User collaborator) {
        if (board.getUserBoards().stream().anyMatch(boardUser -> boardUser.getCollaborator().equals(collaborator))) {
            throw new IllegalArgumentException("이미 등록된 멤버입니다");
        }
        UserBoard userBoard = new UserBoard(collaborator, board);
        board.getUserBoards().add(userBoard);

    }

    public UserBoard findCollaborator(Long userId) {
        return userBoardRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 board에 존재하지 않는 사용자입니다."));
    }
}
