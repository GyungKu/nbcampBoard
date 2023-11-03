package com.sparta.sparta_board.service;

import com.sparta.sparta_board.entity.Board;
import com.sparta.sparta_board.entity.BoardRequestDto;
import com.sparta.sparta_board.entity.BoardResponseDto;
import com.sparta.sparta_board.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;
    
    @Test
    @DisplayName("게시글 생성 테스트")
    void test1() {
        BoardRequestDto request = new BoardRequestDto("제목", "1234", "userA", "내용");
        BoardResponseDto response = boardService.createBoard(request);

        Board findBoard = boardRepository.findById(response.getId()).get();
        Assertions.assertThat(findBoard.getId()).isEqualTo(response.getId());

        Assertions.assertThat(findBoard.getUsername()).isEqualTo(request.getUsername());
        Assertions.assertThat(findBoard.getTitle()).isEqualTo(request.getTitle());
        Assertions.assertThat(findBoard.getContent()).isEqualTo(request.getContent());
        Assertions.assertThat(findBoard.getPassword()).isEqualTo(request.getPassword());
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    void test2() {
        BoardRequestDto request = new BoardRequestDto("제목", "1234", "userA", "내용");
        BoardResponseDto response = boardService.createBoard(request);

        BoardResponseDto findResponse = boardService.getBoard(response.getId());

        Assertions.assertThat(findResponse.getId()).isEqualTo(response.getId());
    }
}