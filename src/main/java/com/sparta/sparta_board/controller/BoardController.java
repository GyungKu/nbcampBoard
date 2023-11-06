package com.sparta.sparta_board.controller;

import com.sparta.sparta_board.dto.BoardRequestDto;
import com.sparta.sparta_board.dto.BoardResponseDto;
import com.sparta.sparta_board.dto.GlobalResponseDto;
import com.sparta.sparta_board.exception.PasswordException;
import com.sparta.sparta_board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalResponseDto> illegalArgsHandler(IllegalArgumentException e) {
        return status(NOT_FOUND).body(new GlobalResponseDto("not found", e.getMessage()));
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<GlobalResponseDto> passwordHandler(PasswordException e) {
        return status(UNAUTHORIZED).body(new GlobalResponseDto("invalid password", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponseDto> validationExceptionHandler(MethodArgumentNotValidException e) {
        HashMap<String, String> errors = new HashMap<>();
        e.getAllErrors().forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));

        return badRequest().body(new GlobalResponseDto("validation error", errors));
    }



    @PostMapping("/board")
    public ResponseEntity<GlobalResponseDto> createBoard(@RequestBody @Valid BoardRequestDto boardRequestDto) {
        BoardResponseDto board = boardService.createBoard(boardRequestDto);
        return status(CREATED).body(new GlobalResponseDto("create success", board));
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<GlobalResponseDto> getBoard(@PathVariable Long id) {
        BoardResponseDto board = boardService.getBoard(id);
        return ok(new GlobalResponseDto("board", board));
    }

    @GetMapping("/boards")
    public ResponseEntity<GlobalResponseDto> getBoards() {
        List<BoardResponseDto> boards = boardService.getBoards();
        return ok(new GlobalResponseDto("board list", boards));
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<GlobalResponseDto> updateBoard(@PathVariable Long id, @RequestBody @Valid BoardRequestDto boardRequestDto) {
        BoardResponseDto board = boardService.updateBoard(id, boardRequestDto);
        return ok(new GlobalResponseDto("update success", board));
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<GlobalResponseDto> deleteBoard(@PathVariable Long id, @RequestBody Map<String, String> passwordMap) {
        String password = passwordMap.get("password");
        boardService.deleteBoard(id, password);
        return ok(new GlobalResponseDto("delete success", id));
    }

}
