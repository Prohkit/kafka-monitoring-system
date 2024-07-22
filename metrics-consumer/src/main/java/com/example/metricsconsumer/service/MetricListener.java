package com.example.metricsconsumer.service;


import com.example.dto.metric.MetricDto;
import com.example.metricsconsumer.mapper.MetricMapper;
import com.example.metricsconsumer.model.Metric;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MetricListener {

    private final MetricMapper metricMapper;

    private final MetricService metricService;

    @KafkaListener(topics = "metrics-topic", groupId = "metrics-topic-events")
    public void handle(MetricDto metricDto) {
        Metric metric = metricMapper.toMetric(metricDto);
        metricService.saveMetric(metric);
    }
}
