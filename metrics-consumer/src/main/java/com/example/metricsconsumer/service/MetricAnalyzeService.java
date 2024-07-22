package com.example.metricsconsumer.service;

import com.example.metricsconsumer.model.Metric;
import com.example.metricsconsumer.model.Sample;
import com.example.metricsconsumer.model.Statistic;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MetricAnalyzeService {

    private final MetricService metricService;

    private final StatisticService statisticService;

    public void analyzeStatisticByMetricCreatedTimeAgo(String metricName, LocalDateTime time) {
        List<Metric> metricList = metricService.getMetricsByMetricNameAndCreatedTimeAfter(metricName, time);
        LocalDateTime currentTime = LocalDateTime.now();
        if (metricList.isEmpty()) {
            log.info("За промежуток с {} по {}, метрик не обнаружено.", time, LocalDateTime.now());
        }
        List<Double> values = getValuesFromMeasurements(metricList);
        DoubleSummaryStatistics summaryStatistics = getSummaryStatistics(values);
        Statistic statistic = statisticService.createStatistic(metricName, time, currentTime, summaryStatistics);
        statisticService.saveStatistic(statistic);
    }

    public List<Double> getValuesFromMeasurements(List<Metric> metrics) {
        List<Double> values = new ArrayList<>();
        for (Metric metric : metrics) {
            List<Sample> measurements = metric.getMeasurements();
            for (Sample measurement : measurements) {
                values.add(measurement.getValue());
            }
        }
        return values;
    }

    public DoubleSummaryStatistics getSummaryStatistics(List<Double> values) {
        return values.stream().collect(Collectors.summarizingDouble(Double::doubleValue));
    }


}
