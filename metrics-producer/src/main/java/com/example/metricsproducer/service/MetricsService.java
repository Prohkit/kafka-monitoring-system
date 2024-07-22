package com.example.metricsproducer.service;

import com.example.dto.metric.AvailableTagDto;
import com.example.dto.metric.MetricDto;
import com.example.dto.metric.SampleDto;
import com.example.metricsproducer.configuration.MetricsConfigurationProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MetricsService {

    private final MetricsEndpoint metricsEndpoint;

    private final MetricsConfigurationProperties metricsProperties;

    public List<MetricDto> getMetricDtos() {
        List<MetricsEndpoint.MetricDescriptor> metricDescriptors = getMetricDescriptors();
        LocalDateTime creationTime = LocalDateTime.now();
        List<MetricDto> metricDtos = new ArrayList<>(metricDescriptors.size());
        for (MetricsEndpoint.MetricDescriptor metricDescriptor : metricDescriptors) {
            if (metricDescriptor != null) {
                metricDtos.add(getMetricDtoFromMetricDescriptor(metricDescriptor, creationTime));
            }
        }
        return metricDtos;
    }

    private List<MetricsEndpoint.MetricDescriptor> getMetricDescriptors() {
        return metricsProperties.getNames().stream()
                .map(name -> metricsEndpoint.metric(name, null))
                .toList();
    }

    private MetricDto getMetricDtoFromMetricDescriptor(MetricsEndpoint.MetricDescriptor metricDescriptor,
                                                       LocalDateTime creationTime) {
        List<SampleDto> measurements = getMeasurementsFromMetricDescriptor(metricDescriptor);
        List<AvailableTagDto> availableTagDtos = getAvailableTagsDtoFromMetricDescriptor(metricDescriptor);
        return new MetricDto(metricDescriptor.getName(),
                metricDescriptor.getDescription(),
                metricDescriptor.getBaseUnit(),
                creationTime,
                measurements,
                availableTagDtos
        );
    }

    private List<SampleDto> getMeasurementsFromMetricDescriptor(MetricsEndpoint.MetricDescriptor metricDescriptor) {
        List<MetricsEndpoint.Sample> measurementsOriginal = metricDescriptor.getMeasurements();
        List<SampleDto> measurements = new ArrayList<>(measurementsOriginal.size());
        for (MetricsEndpoint.Sample sample : measurementsOriginal) {
            measurements.add(new SampleDto(sample.getStatistic() + "", sample.getValue()));
        }
        return measurements;
    }

    private List<AvailableTagDto> getAvailableTagsDtoFromMetricDescriptor(MetricsEndpoint.MetricDescriptor metricDescriptor) {
        List<MetricsEndpoint.AvailableTag> availableTagsOriginal = metricDescriptor.getAvailableTags();
        List<AvailableTagDto> availableTagDtos = new ArrayList<>(availableTagsOriginal.size());
        for (MetricsEndpoint.AvailableTag availableTag : availableTagsOriginal) {
            availableTagDtos.add(new AvailableTagDto(availableTag.getTag(), availableTag.getValues()));
        }
        return availableTagDtos;
    }
}
