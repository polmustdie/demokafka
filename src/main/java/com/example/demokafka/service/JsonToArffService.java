package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.weka.*;
import com.example.demokafka.weka.utils.CsvArffConverter;
import com.example.demokafka.weka.utils.JsonToCsv;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonToArffService {
    @Value("${json.path}")
    private String jsonPath;

//    private String jsonPath = "/Users/polinanesterova/Downloads/demokafka/src/main/resources/data.json";
    @Value("${csv.path}")
    private String csvPath;
//    private String csvPath = "/Users/polinanesterova/Downloads/demokafka/src/main/resources/dataFromCsv.csv";
    @Value("${arff.path}")
    private String arffData;
//    private String arffData = "/Users/polinanesterova/Downloads/demokafka/src/main/resources/dataArff.arff";
    private ObjectMapper mapper;
    public String convert(ArrayList<BatchGeoData> batchGeoData) {
        mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {

            writer.writeValue(new File(jsonPath), batchGeoData);
            JsonToCsv.createCsv(jsonPath);
            CsvArffConverter.convertCsvToArff(csvPath, arffData);
        }
        catch (IOException e){
            System.out.println("Caught IOException in converting");
            e.printStackTrace();
        }
        return arffData;



    }
    public String convert(BatchGeoData[] batchGeoData) {
        mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File(jsonPath), batchGeoData);
            JsonToCsv.createCsv(jsonPath);
            CsvArffConverter.convertCsvToArff(csvPath, arffData);
        }
        catch (IOException e){
            System.out.println("Caught IOException in converting");
            e.printStackTrace();
        }
        return arffData;



    }
}
