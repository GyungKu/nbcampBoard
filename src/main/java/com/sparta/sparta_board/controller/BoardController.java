package com.sparta.sparta_board.controller;

import com.sparta.sparta_board.entity.BoardRequestDto;
import com.sparta.sparta_board.entity.BoardResponseDto;
import com.sparta.sparta_board.exception.PasswordException;
import com.sparta.sparta_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String illegalArgsHandler(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(PasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String passwordHandler(PasswordException e) {
        return e.getMessage();
    }

    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.createBoard(boardRequestDto);
    }

    @GetMapping("/board/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @GetMapping("/boards")
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    @PutMapping("/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.updateBoard(id, boardRequestDto);
    }

    @DeleteMapping("/board/{id}")
    public void deleteBoard(@PathVariable Long id, @RequestBody Map<String, String> passwordMap) {
        String password = passwordMap.get("password");
        boardService.deleteBoard(id, password);
    }

}
