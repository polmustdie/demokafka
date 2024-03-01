package com.example.demokafka.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@AllArgsConstructor
public class BatchGeoData {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
    private Double x;
    private Double y;
    private String flag;

    public BatchGeoData() {

    }
}

