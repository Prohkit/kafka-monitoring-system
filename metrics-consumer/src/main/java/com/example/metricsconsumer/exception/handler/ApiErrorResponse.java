package com.example.metricsconsumer.exception.handler;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiErrorResponse {
    private String description;
    private String code;
    private String exceptionName;
    private String exceptionMessage;
    private List<String> stacktrace;
}
