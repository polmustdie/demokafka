package com.example.demokafka.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class KafkaProperties {
    private String bootstrapServers;
    private String topic;
    private String topic_out;
}
