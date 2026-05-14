package com.nhnacademy.team4.taskapi.global.exception;

public record FieldErrorResponse(
        String field,
        String message
) {
}