package com.example.demokafka.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@AllArgsConstructor
public class BatchInfoAndData {
    private ArrayList<Object> constants;
    private Collection<BatchGeoData> data;

}
