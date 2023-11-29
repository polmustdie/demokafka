package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.weka.*;
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
public class BatchDBSCANService {
    ObjectMapper mapper;
    @Autowired
    JsonToArffService jsonToArffService;
    public List<BatchGeoData> analyze(BatchGeoData[] batchGeoData) {
        String path = jsonToArffService.convert(batchGeoData);
        System.out.println(path);
        DBSCANs gb = new DBSCANs(path);

        gb.showResults(gb.getNodeset());
        return gb.getNodes();


    }
}
