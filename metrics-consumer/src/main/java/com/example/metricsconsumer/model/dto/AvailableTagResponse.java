package com.example.metricsconsumer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableTagResponse {
    private String tag;
    private Set<String> values;
}
