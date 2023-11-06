package com.sparta.sparta_board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class BoardRequestDto {

    @NotEmpty(message = "공백이 될 수 없습니다.")
    @Length(min = 3, max = 30, message = "최소 3자 최대 30자 범위 내에서 입력해 주세요")
    private String title;

    @NotEmpty(message = "공백이 될 수 없습니다.")
    @Length(min = 6, max = 30, message = "최소 6자 최대 30자 범위 내에서 입력해 주세요")
    private String password;

    @NotEmpty(message = "공백이 될 수 없습니다.")
    @Length(min = 3, max = 20, message = "최소 3자 최대 20자 범위 내에서 입력해 주세요")
    private String username;

    @NotEmpty(message = "공백이 될 수 없습니다.")
    @Length(min = 10, max = 500, message = "최소 10자 최대 500자 범위 내에서 입력해 주세요")
    private String content;
}
