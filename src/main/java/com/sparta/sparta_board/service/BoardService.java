package com.sparta.sparta_board.service;

import com.sparta.sparta_board.entity.Board;
import com.sparta.sparta_board.entity.BoardRequestDto;
import com.sparta.sparta_board.entity.BoardResponseDto;
import com.sparta.sparta_board.exception.PasswordException;
import com.sparta.sparta_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        Board saveBoard = boardRepository.save(new Board(boardRequestDto));
        return new BoardResponseDto(saveBoard);
    }

    public List<BoardResponseDto> getBoards() {
        return boardRepository.findAllByOrderByCreateAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    public BoardResponseDto getBoard(Long id) {
        Board board = findBoard(id);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = findBoard(id);
        passwordCheck(boardRequestDto.getPassword(), board.getPassword());
        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }

    public void deleteBoard(Long id, String password) {
        Board board = findBoard(id);
        passwordCheck(password, board.getPassword());
        boardRepository.delete(board);
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
    }

    private void passwordCheck(String inputPassword, String password) {
        if (!inputPassword.equals(password)) {
            throw new PasswordException();
        }
    }
}
