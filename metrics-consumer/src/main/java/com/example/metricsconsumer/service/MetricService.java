package com.example.metricsconsumer.service;

import com.example.metricsconsumer.mapper.MetricMapper;
import com.example.metricsconsumer.model.Metric;
import com.example.metricsconsumer.model.RefDescription;
import com.example.metricsconsumer.model.RefMetricName;
import com.example.metricsconsumer.model.dto.MetricResponse;
import com.example.metricsconsumer.repository.MetricRepository;
import com.example.metricsconsumer.repository.RefDescriptionRepository;
import com.example.metricsconsumer.repository.RefMetricNameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MetricService {

    private final MetricRepository metricRepository;

    private final RefMetricNameRepository nameRepository;

    private final RefDescriptionRepository descriptionRepository;

    private final AvailableTagService availableTagService;

    private final MetricMapper metricMapper;

    public void saveMetric(Metric metric) {
        boolean existsMetricName = saveRefMetricNameIfNotExists(metric.getMetricName());
        if (existsMetricName) {
            RefMetricName metricNameFromDb = nameRepository.findByNameIgnoreCase(metric.getMetricName().getName());
            metric.getMetricName().setId(metricNameFromDb.getId());
        }
        boolean existsDescription = saveRefDescriptionIfNotExists(metric.getMetricDescription());
        if (existsDescription) {
            RefDescription metricDescriptionFromDb =
                    descriptionRepository.findByDescriptionIgnoreCase(metric.getMetricDescription().getDescription());
            metric.getMetricDescription().setId(metricDescriptionFromDb.getId());
        }
        availableTagService.saveAvailableTags(metric.getAvailableTags());
        metricRepository.save(metric);
    }

    private boolean saveRefMetricNameIfNotExists(RefMetricName metricName) {
        boolean existsMetricName = nameRepository.existsByNameIgnoreCase(metricName.getName());
        if (!existsMetricName) {
            nameRepository.save(metricName);
        }
        return existsMetricName;
    }

    private boolean saveRefDescriptionIfNotExists(RefDescription description) {
        boolean existsDescription = descriptionRepository
                .existsByDescriptionIgnoreCase(description.getDescription());
        if (!existsDescription) {
            descriptionRepository.save(description);
        }
        return existsDescription;
    }

    public List<Metric> getAllMetrics() {
        return metricRepository.findAll();
    }

    public List<MetricResponse> getAllMetricResponses() {
        List<Metric> metrics = getAllMetrics();
        return metricMapper.toMetricResponseList(metrics);
    }

    public Metric getMetricById(Long id) {
        return metricRepository.getMetricById(id);
    }

    public MetricResponse getMetricResponseById(Long id) {
        Metric metric = getMetricById(id);
        return metricMapper.toMetricResponse(metric);
    }

    public List<Metric> getMetricsByMetricNameAndCreatedTimeAfter(String metricName, LocalDateTime startDate) {
        RefMetricName refMetricName = nameRepository.findByNameIgnoreCase(metricName);
        return metricRepository.findMetricsByMetricNameAndCreatedAtAfter(refMetricName, startDate);
    }
}
