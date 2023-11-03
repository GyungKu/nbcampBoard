package com.sparta.sparta_board.service;

import com.sparta.sparta_board.entity.Board;
import com.sparta.sparta_board.entity.BoardRequestDto;
import com.sparta.sparta_board.entity.BoardResponseDto;
import com.sparta.sparta_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        return new BoardResponseDto(board);
    }
}
