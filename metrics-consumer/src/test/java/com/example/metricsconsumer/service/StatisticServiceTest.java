package com.example.metricsconsumer.service;

import com.example.metricsconsumer.mapper.StatisticMapper;
import com.example.metricsconsumer.model.RefMetricName;
import com.example.metricsconsumer.model.Statistic;
import com.example.metricsconsumer.repository.RefMetricNameRepository;
import com.example.metricsconsumer.repository.StatisticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {

    @Mock
    private StatisticRepository statisticRepository;

    @Mock
    private RefMetricNameRepository metricNameRepository;

    @Mock
    private StatisticMapper statisticMapper;

    @InjectMocks
    private StatisticService statisticService;

    private Statistic statistic;

    private RefMetricName metricName;

    @BeforeEach
    void setUp() {
        statistic = new Statistic();
        metricName = new RefMetricName(null, "metricName");
        statistic.setMetricName(metricName);
        statistic.setStartTime(LocalDateTime.now());
        statistic.setEndTime(LocalDateTime.now());
        statistic.setMax(3D);
        statistic.setAverage(2D);
        statistic.setMin(1D);
    }

    @Test
    void createStatistic() {
        DoubleSummaryStatistics summaryStatistics = new DoubleSummaryStatistics(2, 1D, 3D, 4D);
        when(metricNameRepository.findByNameIgnoreCase("metricName"))
                .thenReturn(metricName);


        Statistic result = statisticService.createStatistic(statistic.getMetricName().getName(),
                statistic.getStartTime(),
                statistic.getEndTime(),
                summaryStatistics);

        assertEquals(statistic, result);
    }

    @Test
    void saveStatistic() {
        statisticService.saveStatistic(statistic);

        verify(statisticRepository).save(statistic);
    }

    @Test
    void getStatisticById() {
        Long id = 1L;

        statisticService.getStatisticById(id);

        verify(statisticRepository).getStatisticById(id);
    }

    @Test
    void getStatisticResponseById() {
        Long id = 1L;
        when(statisticRepository.getStatisticById(id)).thenReturn(statistic);

        statisticService.getStatisticResponseById(id);

        verify(statisticMapper).toStatisticResponse(statistic);
    }

    @Test
    void getAllStatistics() {
        statisticService.getAllStatistics();

        verify(statisticRepository).findAll();
    }

    @Test
    void getAllStatisticResponses() {
        when(statisticRepository.findAll()).thenReturn(List.of(statistic));

        statisticService.getAllStatisticResponses();

        verify(statisticMapper).toStatisticResponseList(List.of(statistic));
    }
}