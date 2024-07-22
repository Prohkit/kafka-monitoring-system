package com.example.metricsproducer.controller;

import com.example.dto.metric.MetricDto;
import com.example.metricsproducer.service.KafkaService;
import com.example.metricsproducer.service.MetricsService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProducerController {
    private final KafkaService kafkaService;

    private final MetricsService metricsService;

    @PostMapping("/metrics")
    @Scheduled(fixedDelay = 5000)
    public void sendMetrics() {
        List<MetricDto> metricDtos = metricsService.getMetricDtos();
        kafkaService.sendMetrics(metricDtos);
    }
}
