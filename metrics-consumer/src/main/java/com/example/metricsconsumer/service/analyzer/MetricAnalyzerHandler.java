package com.example.metricsconsumer.service.analyzer;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MetricAnalyzerHandler {

    private final List<MetricAnalyzer> metricAnalyzerList;

    @Scheduled(cron = "0 * * * * *")
    public void analyze() {
        metricAnalyzerList.forEach(MetricAnalyzer::analyzeMetric);
    }
}
