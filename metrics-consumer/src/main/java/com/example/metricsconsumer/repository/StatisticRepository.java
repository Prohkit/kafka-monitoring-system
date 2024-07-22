package com.example.metricsconsumer.repository;

import com.example.metricsconsumer.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    Statistic getStatisticById(Long id);
}
