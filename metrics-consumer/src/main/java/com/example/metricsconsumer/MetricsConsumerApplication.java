package com.example.metricsconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MetricsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetricsConsumerApplication.class, args);
    }

}
