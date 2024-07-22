package com.example.metricsconsumer.mapper;

import com.example.dto.metric.SampleDto;
import com.example.metricsconsumer.model.Sample;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SampleMapper {

    Sample toSample(SampleDto sampleDto);
}
