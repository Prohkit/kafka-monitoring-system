package com.example.metricsconsumer.repository;

import com.example.metricsconsumer.model.RefAvailableTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefAvailableTagRepository extends JpaRepository<RefAvailableTag, Long> {
    boolean existsRefAvailableTagIgnoreCaseByTag(String tag);

    RefAvailableTag getRefAvailableTagIgnoreCaseByTag(String tag);
}
