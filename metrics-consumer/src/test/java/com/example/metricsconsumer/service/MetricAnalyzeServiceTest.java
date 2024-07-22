package com.example.metricsconsumer.service;

import com.example.metricsconsumer.model.Metric;
import com.example.metricsconsumer.model.Sample;
import com.example.metricsconsumer.model.Statistic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetricAnalyzeServiceTest {

    @Mock
    private MetricService metricService;

    @Mock
    private StatisticService statisticService;

    @InjectMocks
    private MetricAnalyzeService metricAnalyzeService;

    @Test
    void analyzeStatisticByMetricCreatedTimeAgo() {
        LocalDateTime minuteAgo = LocalDateTime.now().minusMinutes(1);
        Metric metric = new Metric();
        Statistic statistic = new Statistic();
        metric.setMeasurements(List.of(
                new Sample(null, "VALUE", 1D, null),
                new Sample(null, "VALUE", 2D, null),
                new Sample(null, "VALUE", 3D, null)));
        when(metricService.getMetricsByMetricNameAndCreatedTimeAfter("metricName", minuteAgo))
                .thenReturn(List.of(metric));
        when(statisticService.createStatistic(any(String.class),
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(DoubleSummaryStatistics.class))).thenReturn(statistic);

        metricAnalyzeService.analyzeStatisticByMetricCreatedTimeAgo("metricName", minuteAgo);

        verify(statisticService).saveStatistic(statistic);
    }
}