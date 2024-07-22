package com.example.metricsconsumer.mapper;

import com.example.metricsconsumer.model.Statistic;
import com.example.metricsconsumer.model.dto.StatisticResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatisticMapper {

    @Mapping(source = "statistic.metricName.name", target = "metricName")
    StatisticResponse toStatisticResponse(Statistic statistic);

    List<StatisticResponse> toStatisticResponseList(List<Statistic> statisticList);
}
