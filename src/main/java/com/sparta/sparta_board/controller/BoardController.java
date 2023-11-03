package com.sparta.sparta_board.controller;

import com.sparta.sparta_board.entity.BoardRequestDto;
import com.sparta.sparta_board.entity.BoardResponseDto;
import com.sparta.sparta_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.createBoard(boardRequestDto);
    }

}
