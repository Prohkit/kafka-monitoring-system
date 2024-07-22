package com.example.metricsconsumer.model.dto;

import com.example.metricsconsumer.model.Sample;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricResponse {
    private Long id;
    private String name;
    private String description;
    private String baseUnit;
    private LocalDateTime createdAt;
    private List<Sample> measurements;
    private List<AvailableTagResponse> availableTags;
}
