package com.example.metricsconsumer.service;

import com.example.metricsconsumer.model.AvailableTag;
import com.example.metricsconsumer.model.AvailableTagValue;
import com.example.metricsconsumer.model.RefAvailableTag;
import com.example.metricsconsumer.model.RefAvailableTagValue;
import com.example.metricsconsumer.repository.AvailableTagRepository;
import com.example.metricsconsumer.repository.RefAvailableTagRepository;
import com.example.metricsconsumer.repository.RefAvailableTagValueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AvailableTagService {
    private final AvailableTagRepository availableTagRepository;

    private final RefAvailableTagRepository refAvailableTagRepository;

    private final RefAvailableTagValueRepository refAvailableTagValueRepository;

    public void saveAvailableTags(List<AvailableTag> availableTagList) {
        for (AvailableTag availableTag : availableTagList) {
            RefAvailableTag refAvailableTag = availableTag.getRefAvailableTag();
            boolean existsRefAvailableTag = saveRefAvailableTagIfNotExists(refAvailableTag);
            if (existsRefAvailableTag) {
                RefAvailableTag refAvailableTagFromDb =
                        refAvailableTagRepository.getRefAvailableTagIgnoreCaseByTag(refAvailableTag.getTag());
                availableTag.setRefAvailableTag(refAvailableTagFromDb);
            }
            prepareAvailableTagValuesToSave(availableTag.getValues());
            availableTagRepository.save(availableTag);
        }
    }

    private boolean saveRefAvailableTagIfNotExists(RefAvailableTag refAvailableTag) {
        boolean existsRefAvailableTag =
                refAvailableTagRepository.existsRefAvailableTagIgnoreCaseByTag(refAvailableTag.getTag());
        if (!existsRefAvailableTag) {
            refAvailableTagRepository.save(refAvailableTag);
        }
        return existsRefAvailableTag;
    }

    private void prepareAvailableTagValuesToSave(Set<AvailableTagValue> availableTagValueSet) {
        for (AvailableTagValue availableTagValue : availableTagValueSet) {
            RefAvailableTagValue refAvailableTagValue = availableTagValue.getRefAvailableTagValue();
            boolean existsRefAvailableTagValue = saveRefAvailableTagValueIfNotExists(refAvailableTagValue);
            if (existsRefAvailableTagValue) {
                RefAvailableTagValue refAvailableTagValueFromDb =
                        refAvailableTagValueRepository
                                .getRefAvailableTagValueIgnoreCaseByValue(refAvailableTagValue.getValue());
                availableTagValue.setRefAvailableTagValue(refAvailableTagValueFromDb);
            }
        }
    }

    private boolean saveRefAvailableTagValueIfNotExists(RefAvailableTagValue refAvailableTagValue) {
        boolean existsRefAvailableTagValue =
                refAvailableTagValueRepository.existsByValueIgnoreCase(refAvailableTagValue.getValue());
        if (!existsRefAvailableTagValue) {
            refAvailableTagValueRepository.save(refAvailableTagValue);
        }
        return existsRefAvailableTagValue;
    }
}
