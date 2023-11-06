package com.sparta.sparta_board.controller;

import com.sparta.sparta_board.dto.BoardRequestDto;
import com.sparta.sparta_board.dto.BoardResponseDto;
import com.sparta.sparta_board.dto.GlobalResponseDto;
import com.sparta.sparta_board.exception.PasswordException;
import com.sparta.sparta_board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(NOT_FOUND)
    public GlobalResponseDto illegalArgsHandler(IllegalArgumentException e) {
        return new GlobalResponseDto("not found", e.getMessage());
    }

    @ExceptionHandler(PasswordException.class)
    @ResponseStatus(UNAUTHORIZED)
    public GlobalResponseDto passwordHandler(PasswordException e) {
        return new GlobalResponseDto("invalid password", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public GlobalResponseDto validationExceptionHandler(MethodArgumentNotValidException e) {
        HashMap<String, String> errors = new HashMap<>();
        e.getAllErrors().forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        return new GlobalResponseDto("validation error", errors);
    }

    @PostMapping("/board")
    public GlobalResponseDto createBoard(@RequestBody @Valid BoardRequestDto boardRequestDto) {
        BoardResponseDto board = boardService.createBoard(boardRequestDto);
        return new GlobalResponseDto("create success", board);
    }

    @GetMapping("/board/{id}")
    public GlobalResponseDto getBoard(@PathVariable Long id) {
        BoardResponseDto board = boardService.getBoard(id);
        return new GlobalResponseDto("board", board);
    }

    @GetMapping("/boards")
    public GlobalResponseDto getBoards() {
        List<BoardResponseDto> boards = boardService.getBoards();
        return new GlobalResponseDto("board list", boards);
    }

    @PutMapping("/board/{id}")
    public GlobalResponseDto updateBoard(@PathVariable Long id, @RequestBody @Valid BoardRequestDto boardRequestDto) {
        BoardResponseDto board = boardService.updateBoard(id, boardRequestDto);
        return new GlobalResponseDto("update success", board);
    }

    @DeleteMapping("/board/{id}")
    public GlobalResponseDto deleteBoard(@PathVariable Long id, @RequestBody Map<String, String> passwordMap) {
        String password = passwordMap.get("password");
        boardService.deleteBoard(id, password);
        return new GlobalResponseDto("delete success", id);
    }

}
