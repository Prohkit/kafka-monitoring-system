package com.example.metricsconsumer.service.analyzer;

import com.example.metricsconsumer.service.MetricAnalyzeService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MetricJvmMemoryUsedAnalyzer extends MetricAnalyzer {

    public MetricJvmMemoryUsedAnalyzer(MetricAnalyzeService metricAnalyzeService) {
        this.metricAnalyzeService = metricAnalyzeService;
        this.metricName = "jvm.memory.used";
    }

    @Override
    public void analyzeMetric() {
        LocalDateTime minuteAgoTime = LocalDateTime.now().minusMinutes(1);
        metricAnalyzeService.analyzeStatisticByMetricCreatedTimeAgo(metricName, minuteAgoTime);
    }
}
