package com.example.metricsconsumer.service;

import com.example.metricsconsumer.exception.InfiniteValueException;
import com.example.metricsconsumer.mapper.StatisticMapper;
import com.example.metricsconsumer.model.RefMetricName;
import com.example.metricsconsumer.model.Statistic;
import com.example.metricsconsumer.model.dto.StatisticResponse;
import com.example.metricsconsumer.repository.RefMetricNameRepository;
import com.example.metricsconsumer.repository.StatisticRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@AllArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;

    private final RefMetricNameRepository metricNameRepository;

    private final StatisticMapper statisticMapper;

    public Statistic createStatistic(String metricName,
                                     LocalDateTime startTime,
                                     LocalDateTime endTime,
                                     DoubleSummaryStatistics summaryStatistics) {
        Double max = summaryStatistics.getMax();
        Double min = summaryStatistics.getMin();
        Double average = summaryStatistics.getAverage();
        if (max.isInfinite() || min.isInfinite()) {
            throw new InfiniteValueException("Infinity value obtained during DoubleSummaryStatistics processing");
        }
        RefMetricName refMetricName = metricNameRepository.findByNameIgnoreCase(metricName);
        return new Statistic(null, refMetricName, startTime, endTime, max, min, average);
    }

    public void saveStatistic(Statistic statistic) {
        statisticRepository.save(statistic);
    }

    public Statistic getStatisticById(Long id) {
        return statisticRepository.getStatisticById(id);
    }
    public StatisticResponse getStatisticResponseById(Long id) {
        Statistic statistic = getStatisticById(id);
        return statisticMapper.toStatisticResponse(statistic);
    }

    public List<Statistic> getAllStatistics() {
        return statisticRepository.findAll();
    }

    public List<StatisticResponse> getAllStatisticResponses() {
        List<Statistic> statisticList = getAllStatistics();
        return statisticMapper.toStatisticResponseList(statisticList);
    }
}
