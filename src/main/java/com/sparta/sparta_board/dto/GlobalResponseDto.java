package com.sparta.sparta_board.dto;

import lombok.Getter;

@Getter
public class GlobalResponseDto {
    String message;
    Object data;

    public GlobalResponseDto(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
