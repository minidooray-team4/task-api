package com.nhnacademy.team4.taskapi.global.exception;

import lombok.Getter;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

@Getter
public class BusinessException extends ErrorResponseException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getStatus(), createProblemDetail(errorCode), null);
        this.errorCode = errorCode;
    }

    private static ProblemDetail createProblemDetail(ErrorCode errorCode) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                errorCode.getStatus(),
                errorCode.getMessage()
        );

        problemDetail.setTitle(errorCode.getCode());
        problemDetail.setProperty("code", errorCode.getCode());

        return problemDetail;
    }
}