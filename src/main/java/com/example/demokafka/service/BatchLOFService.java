package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchInfoAndData;
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
    public List<BatchGeoData> analyze(BatchInfoAndData batch) throws Exception {
//        final String json = "{\"contentType\": \"foo\", \"fooField1\": ... }";
//        final JsonNode node = new ObjectMapper().readTree(json);
////                    ^^^^^^^^^^^^^^^^^^
//// n.b.: try and *reuse* a single instance of ObjectMapper in production
//
//        if (node.has("contentType")) {
//            System.out.println("contentType: " + node.get("contentType"));
//        }
        ArrayList<Object> constants = batch.getConstants();

        String path = jsonToArffService.convert(batch.getData());
        LocalOutlierFactor gb = new LocalOutlierFactor(path, constants);



        gb.showResults(gb.getNodeset());
        return gb.getNodes();


    }
}
