package com.example.demokafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Component
@AllArgsConstructor
public class BatchGeoDataToController {
    private ArrayList<Object> constants;
    private Collection<GeoDataFlag> data;
}

