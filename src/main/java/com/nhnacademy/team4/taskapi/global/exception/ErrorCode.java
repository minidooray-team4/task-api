package com.nhnacademy.team4.taskapi.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    TEMP_HOLDER(HttpStatus.BAD_REQUEST, "TEMP_HOLDER", "임시 에러 코드입니다."),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND,"PROJECT_NOT_FOUND","프로젝트를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}