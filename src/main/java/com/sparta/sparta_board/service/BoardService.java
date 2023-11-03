package com.sparta.sparta_board.service;

import com.sparta.sparta_board.entity.Board;
import com.sparta.sparta_board.entity.BoardRequestDto;
import com.sparta.sparta_board.entity.BoardResponseDto;
import com.sparta.sparta_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        Board saveBoard = boardRepository.save(new Board(boardRequestDto));
        return new BoardResponseDto(saveBoard);
    }
}
