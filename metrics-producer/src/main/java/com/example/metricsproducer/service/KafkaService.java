package com.example.metricsproducer.service;

import com.example.dto.metric.MetricDto;
import com.example.metricsproducer.configuration.MetricsConfigurationProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaService {
    private final KafkaTemplate<String, MetricDto> kafkaTemplate;

    private final MetricsConfigurationProperties metricsProperties;


    public void sendMetrics(List<MetricDto> metricDtos) {
        for (MetricDto metricDto : metricDtos) {
            CompletableFuture<SendResult<String, MetricDto>> future
                    = kafkaTemplate.send(metricsProperties.getTopicName(), metricDto);
            future.whenComplete((result, exception) -> {
                if (exception != null) {
                    log.error("Failed to send metric: {}", exception.getMessage());
                } else {
                    log.info("Metric send successfully: {}", result.getRecordMetadata());
                }
            });
        }
    }
}
