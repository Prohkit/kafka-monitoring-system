package com.example.metricsconsumer.repository;

import com.example.metricsconsumer.model.RefAvailableTagValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefAvailableTagValueRepository extends JpaRepository<RefAvailableTagValue, Long> {
    boolean existsByValueIgnoreCase(String value);

    RefAvailableTagValue getRefAvailableTagValueIgnoreCaseByValue(String value);
}
