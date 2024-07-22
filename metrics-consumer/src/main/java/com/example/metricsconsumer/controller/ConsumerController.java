package com.example.metricsconsumer.controller;

import com.example.metricsconsumer.model.dto.MetricResponse;
import com.example.metricsconsumer.model.dto.StatisticResponse;
import com.example.metricsconsumer.service.MetricService;
import com.example.metricsconsumer.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ConsumerController {

    private final MetricService metricService;

    private final StatisticService statisticService;

    @GetMapping("/metrics")
    public ResponseEntity<List<MetricResponse>> getMetrics() {
        List<MetricResponse> metricResponses = metricService.getAllMetricResponses();
        return ResponseEntity.ok(metricResponses);
    }

    @GetMapping("/metrics/{id}")
    public ResponseEntity<MetricResponse> getMetricById(@PathVariable Long id) {
        MetricResponse metricResponse = metricService.getMetricResponseById(id);
        return ResponseEntity.ok(metricResponse);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<StatisticResponse>> getStatisticResponses() {
        List<StatisticResponse> statisticResponses = statisticService.getAllStatisticResponses();
        return ResponseEntity.ok(statisticResponses);
    }

    @GetMapping("/statistics/{id}")
    public ResponseEntity<StatisticResponse> getStatisticById(@PathVariable Long id) {
        StatisticResponse statisticResponse = statisticService.getStatisticResponseById(id);
        return ResponseEntity.ok(statisticResponse);
    }
}
