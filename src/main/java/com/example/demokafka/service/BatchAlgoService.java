package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchInfoAndData;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.util.List;

@Service
public abstract class BatchAlgoService {
    protected JsonToArffService jsonToArffService = new JsonToArffService();

    public abstract List<BatchGeoData> analyze(BatchInfoAndData batchGeoData) throws ParseException;
}
