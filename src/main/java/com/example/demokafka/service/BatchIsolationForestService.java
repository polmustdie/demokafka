package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchInfoAndData;
import com.example.demokafka.weka.DBSCANs;
import com.example.demokafka.weka.IsolationForests;
import com.example.demokafka.weka.utils.CsvArffConverter;
import com.example.demokafka.weka.utils.JsonToCsv;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchIsolationForestService {
    @Autowired
    JsonToArffService jsonToArffService;
    public List<BatchGeoData> analyze(BatchInfoAndData batch) throws Exception {
        ArrayList<Object> constants = batch.getConstants();
        String path = jsonToArffService.convert(batch.getData());
        IsolationForests gb = new IsolationForests(path, constants);

        gb.showResults(gb.getNodeset());
        return gb.getNodes();


    }
}
