package com.example.dto.metric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableTagDto {
    private String tag;
    private Set<String> values;
}
