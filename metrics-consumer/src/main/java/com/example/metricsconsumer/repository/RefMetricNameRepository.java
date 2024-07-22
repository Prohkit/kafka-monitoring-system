package com.example.metricsconsumer.repository;

import com.example.metricsconsumer.model.RefMetricName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefMetricNameRepository extends JpaRepository<RefMetricName, Long> {
    boolean existsByNameIgnoreCase(String metricName);
    RefMetricName findByNameIgnoreCase(String metricName);
}
