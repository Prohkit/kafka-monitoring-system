package com.example.metricsconsumer.exception.handler;

import com.example.metricsconsumer.exception.InfiniteValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(InfiniteValueException.class)
    public ResponseEntity<ApiErrorResponse> infiniteValueException(InfiniteValueException exception) {
        ApiErrorResponse apiErrorResponse = getApiErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        apiErrorResponse.setExceptionName("InfiniteValueException");
        apiErrorResponse.setDescription("Infinity value obtained.");
        return ResponseEntity
                .internalServerError()
                .body(apiErrorResponse);
    }

    private ApiErrorResponse getApiErrorResponse(Exception exception, HttpStatus httpStatus) {
        return ApiErrorResponse.builder()
                .exceptionMessage(exception.getMessage())
                .code(String.valueOf(httpStatus))
                .stacktrace(Arrays.stream(exception.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList())
                .build();
    }
}
