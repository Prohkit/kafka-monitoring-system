package com.example.metricsconsumer.service.analyzer;

import com.example.metricsconsumer.service.MetricAnalyzeService;

public abstract class MetricAnalyzer {

    protected MetricAnalyzeService metricAnalyzeService;

    protected String metricName;

    public abstract void analyzeMetric();
}
