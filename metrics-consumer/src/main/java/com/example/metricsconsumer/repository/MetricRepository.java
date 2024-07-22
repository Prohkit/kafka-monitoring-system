package com.example.metricsconsumer.repository;

import com.example.metricsconsumer.model.Metric;
import com.example.metricsconsumer.model.RefMetricName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
    Metric getMetricById(Long id);

    List<Metric> findMetricsByMetricNameAndCreatedAtAfter(RefMetricName metricName, LocalDateTime startTime);
}
