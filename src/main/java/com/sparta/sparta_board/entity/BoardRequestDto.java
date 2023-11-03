package com.sparta.sparta_board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardRequestDto {

    private String title;

    private String password;

    private String username;

    private String content;

}
