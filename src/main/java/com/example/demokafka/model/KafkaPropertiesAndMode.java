package com.example.demokafka.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class KafkaPropertiesAndMode {
    private KafkaProperties properties;
    private int mode;
    private ArrayList<Object> constants;
}

