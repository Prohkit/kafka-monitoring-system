package com.example.metricsproducer.configuration;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@AllArgsConstructor
public class KafkaConfiguration {
    private final MetricsConfigurationProperties metricsProperties;

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(metricsProperties.getTopicName())
                .partitions(1)
                .build();
    }
}
