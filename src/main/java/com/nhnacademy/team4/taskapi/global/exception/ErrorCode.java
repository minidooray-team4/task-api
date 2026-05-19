package com.nhnacademy.team4.taskapi.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    TEMP_HOLDER(HttpStatus.BAD_REQUEST, "TEMP_HOLDER", "임시 에러 코드입니다."),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT_NOT_FOUND", "프로젝트를 찾을 수 없습니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "TASK_NOT_FOUND", "테스크를 찾을 수 없습니다."),
    PROJECT_MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "PROJECT_MEMBER_ALREADY_EXISTS", "이미 프로젝트에 존자는 멤버입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "권한이 없습니다."),
    TAG_ALREADY_EXISTS(HttpStatus.CONFLICT,"TAG_ALREADY_EXISTS","이미 있는 태그 입니다."),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND,"TAG_NOT_FOUND","태그를 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
    }