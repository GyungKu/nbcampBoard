package com.sparta.sparta_board.service;

import com.sparta.sparta_board.entity.Board;
import com.sparta.sparta_board.dto.BoardRequestDto;
import com.sparta.sparta_board.dto.BoardResponseDto;
import com.sparta.sparta_board.exception.PasswordException;
import com.sparta.sparta_board.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(findBoard.getId()).isEqualTo(response.getId());

        assertThat(findBoard.getUsername()).isEqualTo(request.getUsername());
        assertThat(findBoard.getTitle()).isEqualTo(request.getTitle());
        assertThat(findBoard.getContent()).isEqualTo(request.getContent());
        assertThat(findBoard.getPassword()).isEqualTo(request.getPassword());
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    void test2() {
        BoardRequestDto request = new BoardRequestDto("제목", "1234", "userA", "내용");
        BoardResponseDto response = boardService.createBoard(request);

        BoardResponseDto findResponse = boardService.getBoard(response.getId());

        assertThat(findResponse.getId()).isEqualTo(response.getId());
    }

    @Test
    @DisplayName("게시글 조회 실패 테스트")
    void test3() {
        assertThatThrownBy(() -> boardService.getBoard(100000L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void test4() {
        // 게시글 저장
        BoardRequestDto request = new BoardRequestDto("제목", "1234", "userA", "내용");
        BoardResponseDto response = boardService.createBoard(request);

        // 저장한 게시글 갖고 오기
        BoardResponseDto findResponse = boardService.getBoard(response.getId());

        // 수정할 내용 받아서 수정
        BoardRequestDto updateRequest = new BoardRequestDto("제목1", "1234", "userB", "내용");
        BoardResponseDto updateResponse = boardService.updateBoard(findResponse.getId(), updateRequest);

        assertThat(updateResponse.getTitle()).isEqualTo(updateRequest.getTitle());
        assertThat(updateResponse.getUsername()).isEqualTo(updateRequest.getUsername());
        assertThat(updateResponse.getContent()).isEqualTo(updateRequest.getContent());
    }

    @Test
    @DisplayName("게시글 수정 실패 테스트")
    void test5() {
        // 게시글 저장
        BoardRequestDto request = new BoardRequestDto("제목", "1234", "userA", "내용");
        BoardResponseDto response = boardService.createBoard(request);

        // 비밀번호가 틀릴 경우
        BoardRequestDto updateRequest = new BoardRequestDto("제목1", "4321", "userB", "내용");
        assertThatThrownBy(() -> boardService.updateBoard(response.getId(), updateRequest)).isInstanceOf(PasswordException.class);
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void test6() {
        // 게시글 저장
        BoardRequestDto request = new BoardRequestDto("제목", "1234", "userA", "내용");
        BoardResponseDto response = boardService.createBoard(request);

        // 게시글 삭제
        boardService.deleteBoard(response.getId(), "1234");

        // 이미 삭제된 게시글 이므로 조회 시 존재 하지 않는 게시글 처리가 됨
        assertThatThrownBy(() -> boardService.getBoard(response.getId())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("게시글 삭제 실패 테스트")
    void test7() {
        // 게시글 저장
        BoardRequestDto request = new BoardRequestDto("제목", "1234", "userA", "내용");
        BoardResponseDto response = boardService.createBoard(request);

        // 게시글 삭제 비밀번호가 틀릴 때
        assertThatThrownBy(() -> boardService.deleteBoard(response.getId(), "4321")).isInstanceOf(PasswordException.class);
    }
}