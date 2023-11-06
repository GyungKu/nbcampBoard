package com.sparta.sparta_board.entity;

import com.sparta.sparta_board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Board extends Timestamped{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 500, nullable = false)
    private String content;

    public Board(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.password = boardRequestDto.getPassword();
        this.username = boardRequestDto.getUsername();
        this.content = boardRequestDto.getContent();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.username = boardRequestDto.getUsername();
        this.content = boardRequestDto.getContent();
    }

}
