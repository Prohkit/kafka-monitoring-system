package com.example.metricsconsumer.service.analyzer;

import com.example.metricsconsumer.service.MetricAnalyzeService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MetricProcessCpuUsageAnalyzer extends MetricAnalyzer {

    public MetricProcessCpuUsageAnalyzer(MetricAnalyzeService metricAnalyzeService) {
        this.metricAnalyzeService = metricAnalyzeService;
        this.metricName = "process.cpu.usage";
    }

    @Override
    public void analyzeMetric() {
        LocalDateTime minuteAgoTime = LocalDateTime.now().minusMinutes(1);
        metricAnalyzeService.analyzeStatisticByMetricCreatedTimeAgo(metricName, minuteAgoTime);
    }
}
