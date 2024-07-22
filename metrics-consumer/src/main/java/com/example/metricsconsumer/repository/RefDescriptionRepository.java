package com.example.metricsconsumer.repository;

import com.example.metricsconsumer.model.RefDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefDescriptionRepository extends JpaRepository<RefDescription, Long> {
    boolean existsByDescriptionIgnoreCase(String description);

    RefDescription findByDescriptionIgnoreCase(String description);
}
