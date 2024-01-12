package com.example.demokafka.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class BatchInfoAndData {
    private ArrayList<Object> constants;
//    private int mode;
    private ArrayList<BatchGeoData> data;

//    public int getMode() {
//        return mode;
//    }
}
