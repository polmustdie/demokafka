package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchInfoAndData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.util.List;

@Service
public abstract class BatchAlgoService {
    @Autowired
    JsonToArffService jsonToArffService;

    public abstract List<BatchGeoData> analyze(BatchInfoAndData batchGeoData) throws ParseException;
}
