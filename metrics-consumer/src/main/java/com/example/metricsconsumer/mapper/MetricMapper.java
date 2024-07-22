package com.example.metricsconsumer.mapper;

import com.example.dto.metric.MetricDto;
import com.example.metricsconsumer.model.Metric;
import com.example.metricsconsumer.model.dto.MetricResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AvailableTagMapper.class, SampleMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MetricMapper {

    @Mapping(source = "metricDto.availableTagDtos", target = "availableTags")
    @Mapping(source = "metricDto.name", target = "metricName.name")
    @Mapping(source = "metricDto.description", target = "metricDescription.description")
    Metric toMetric(MetricDto metricDto);

    @Mapping(source = "metric.metricName.name", target = "name")
    @Mapping(source = "metric.metricDescription.description", target = "description")
    MetricResponse toMetricResponse(Metric metric);

    List<MetricResponse> toMetricResponseList(List<Metric> metrics);
}
