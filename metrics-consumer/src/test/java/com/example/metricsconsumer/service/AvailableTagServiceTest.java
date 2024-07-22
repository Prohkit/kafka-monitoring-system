package com.example.metricsconsumer.service;

import com.example.metricsconsumer.model.AvailableTag;
import com.example.metricsconsumer.model.AvailableTagValue;
import com.example.metricsconsumer.model.RefAvailableTag;
import com.example.metricsconsumer.model.RefAvailableTagValue;
import com.example.metricsconsumer.repository.AvailableTagRepository;
import com.example.metricsconsumer.repository.RefAvailableTagRepository;
import com.example.metricsconsumer.repository.RefAvailableTagValueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailableTagServiceTest {

    @Mock
    private AvailableTagRepository availableTagRepository;

    @Mock
    private RefAvailableTagRepository refAvailableTagRepository;

    @Mock
    private RefAvailableTagValueRepository refAvailableTagValueRepository;

    @InjectMocks
    private AvailableTagService availableTagService;

    private RefAvailableTagValue refAvailableTagValue;
    private AvailableTagValue availableTagValue;
    private RefAvailableTag refAvailableTag;
    private AvailableTag availableTag;

    @BeforeEach
    void setUp() {
        refAvailableTagValue = new RefAvailableTagValue(null, "value");
        availableTagValue = new AvailableTagValue(null, refAvailableTagValue, null);
        refAvailableTag = new RefAvailableTag(null, "tag");
        availableTag = new AvailableTag(null, refAvailableTag, null, Set.of(availableTagValue));
    }

    @Test
    void saveAvailableTags() {
        when(refAvailableTagRepository.getRefAvailableTagIgnoreCaseByTag(refAvailableTag.getTag()))
                .thenReturn(refAvailableTag);
        when(refAvailableTagRepository.existsRefAvailableTagIgnoreCaseByTag(refAvailableTag.getTag()))
                .thenReturn(true);
        when(refAvailableTagRepository.getRefAvailableTagIgnoreCaseByTag(refAvailableTag.getTag()))
                .thenReturn(refAvailableTag);
        when(refAvailableTagValueRepository.existsByValueIgnoreCase(refAvailableTagValue.getValue()))
                .thenReturn(true);
        when(refAvailableTagValueRepository
                .getRefAvailableTagValueIgnoreCaseByValue(refAvailableTagValue.getValue()))
                .thenReturn(refAvailableTagValue);

        availableTagService.saveAvailableTags(List.of(availableTag));

        verify(availableTagRepository).save(availableTag);
    }
}