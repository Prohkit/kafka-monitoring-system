package com.example.metricsconsumer.service;


import com.example.dto.metric.MetricDto;
import com.example.metricsconsumer.mapper.MetricMapper;
import com.example.metricsconsumer.model.Metric;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MetricListener {

    private final MetricMapper metricMapper;

    private final MetricService metricService;

    @RetryableTopic(attempts = "5", backoff = @Backoff(delay = 2000, maxDelay = 10000, multiplier = 2))
    @KafkaListener(topics = "metrics-topic", groupId = "metrics-topic-events")
    public void handle(MetricDto metricDto) {
        Metric metric = metricMapper.toMetric(metricDto);
        metricService.saveMetric(metric);
    }

    @DltHandler
    public void listenDlt(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("Received from DLT: {}, topic: {}, offset: {}", in, topic, offset);
    }
}
