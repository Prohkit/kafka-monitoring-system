package com.example.dto.metric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricDto {
    private String name;
    private String description;
    private String baseUnit;
    private LocalDateTime createdAt;
    private List<SampleDto> measurements;
    private List<AvailableTagDto> availableTagDtos;
}
