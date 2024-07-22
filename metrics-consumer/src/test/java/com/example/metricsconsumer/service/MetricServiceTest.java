package com.example.metricsconsumer.service;

import com.example.metricsconsumer.mapper.MetricMapper;
import com.example.metricsconsumer.model.Metric;
import com.example.metricsconsumer.model.RefDescription;
import com.example.metricsconsumer.model.RefMetricName;
import com.example.metricsconsumer.repository.MetricRepository;
import com.example.metricsconsumer.repository.RefDescriptionRepository;
import com.example.metricsconsumer.repository.RefMetricNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

    @Mock
    private MetricRepository metricRepository;

    @Mock
    private RefMetricNameRepository nameRepository;

    @Mock
    private RefDescriptionRepository descriptionRepository;

    @Mock
    private MetricMapper metricMapper;

    @Mock
    private AvailableTagService availableTagService;

    @InjectMocks
    private MetricService metricService;

    private Metric metric;

    private RefMetricName metricName;
    private RefDescription description;

    @BeforeEach
    void setUp() {
        metric = new Metric();
        metricName = new RefMetricName(null, "metricName");
        description = new RefDescription(null, "description");
        metric.setMetricName(metricName);
        metric.setMetricDescription(description);
        metric.setBaseUnit("baseUnit");
        metric.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void saveMetric() {
        when(nameRepository.existsByNameIgnoreCase(metric.getMetricName().getName())).thenReturn(true);
        when(nameRepository.findByNameIgnoreCase(metric.getMetricName().getName())).thenReturn(metricName);
        when(descriptionRepository.existsByDescriptionIgnoreCase(metric.getMetricDescription().getDescription()))
                .thenReturn(true);
        when(descriptionRepository.findByDescriptionIgnoreCase(metric.getMetricDescription().getDescription()))
                .thenReturn(description);

        metricService.saveMetric(metric);

        verify(metricRepository).save(metric);
    }

    @Test
    void getAllMetrics() {
        metricService.getAllMetrics();

        verify(metricRepository).findAll();
    }

    @Test
    void getAllMetricResponses() {
        when(metricRepository.findAll()).thenReturn(List.of(metric));

        metricService.getAllMetricResponses();

        verify(metricMapper).toMetricResponseList(List.of(metric));
    }

    @Test
    void getMetricById() {
        Long id = 1L;
        metricService.getMetricById(id);

        verify(metricRepository).getMetricById(id);
    }

    @Test
    void getMetricResponseById() {
        Long id = 1L;
        when(metricRepository.getMetricById(id)).thenReturn(metric);

        metricService.getMetricResponseById(id);

        verify(metricMapper).toMetricResponse(metric);
    }

    @Test
    void getMetricsByMetricNameAndCreatedTimeAfter() {
        LocalDateTime minuteAgo = metric.getCreatedAt().minusMinutes(1);
        when(nameRepository.findByNameIgnoreCase(metricName.getName())).thenReturn(metricName);

        metricService.getMetricsByMetricNameAndCreatedTimeAfter(metricName.getName(), minuteAgo);

        verify(metricRepository).findMetricsByMetricNameAndCreatedAtAfter(metricName, minuteAgo);
    }
}