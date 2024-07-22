package com.example.metricsconsumer.mapper;

import com.example.dto.metric.AvailableTagDto;
import com.example.metricsconsumer.model.AvailableTag;
import com.example.metricsconsumer.model.AvailableTagValue;
import com.example.metricsconsumer.model.RefAvailableTag;
import com.example.metricsconsumer.model.RefAvailableTagValue;
import com.example.metricsconsumer.model.dto.AvailableTagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AvailableTagMapper {


    default AvailableTag toAvailableTag(AvailableTagDto availableTagDto) {
        String tag = availableTagDto.getTag();
        RefAvailableTag refAvailableTag = new RefAvailableTag();
        refAvailableTag.setTag(tag);

        Set<String> values = availableTagDto.getValues();
        Set<AvailableTagValue> availableTagValues = new HashSet<>(values.size());
        for (String value : values) {
            RefAvailableTagValue refAvailableTagValue = new RefAvailableTagValue();
            refAvailableTagValue.setValue(value);

            AvailableTagValue availableTagValue = new AvailableTagValue();
            availableTagValue.setRefAvailableTagValue(refAvailableTagValue);

            availableTagValues.add(availableTagValue);
        }
        AvailableTag availableTag = new AvailableTag();
        availableTag.setRefAvailableTag(refAvailableTag);
        availableTag.setValues(availableTagValues);
        return availableTag;
    }

    default AvailableTagResponse toAvailableTagResponse(AvailableTag availableTag) {
        String tag = availableTag.getRefAvailableTag().getTag();
        Set<AvailableTagValue> availableTagValues = availableTag.getValues();
        Set<String> values = new HashSet<>(availableTagValues.size());
        for (AvailableTagValue value : availableTagValues) {
            values.add(value.getRefAvailableTagValue().getValue());
        }
        return new AvailableTagResponse(tag, values);
    }

    List<AvailableTagResponse> toAvailableTagResponseList(List<AvailableTag> availableTagList);
}
