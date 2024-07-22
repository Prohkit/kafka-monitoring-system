package com.example.metricsconsumer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticResponse {
    private Long id;
    private String metricName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double max;
    private Double min;
    private Double average;
}
