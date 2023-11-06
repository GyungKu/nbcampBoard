package com.sparta.sparta_board.exception;

public class PasswordException extends IllegalArgumentException{

    public PasswordException() {
        super("비밀번호가 틀렸습니다.");
    }
}
