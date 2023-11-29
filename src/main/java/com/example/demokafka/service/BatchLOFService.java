package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.weka.LocalOutlierFactor;
import com.example.demokafka.weka.utils.CsvArffConverter;
import com.example.demokafka.weka.utils.JsonToCsv;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchLOFService {
    @Autowired
    JsonToArffService jsonToArffService;
    public List<BatchGeoData> analyze(BatchGeoData[] batchGeoData) throws Exception {
        String path = jsonToArffService.convert(batchGeoData);
        LocalOutlierFactor gb = new LocalOutlierFactor(path);



        gb.showResults(gb.getNodeset());
        return gb.getNodes();


    }
}
