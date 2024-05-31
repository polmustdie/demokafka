package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.weka.utils.CsvArffConverter;
import com.example.demokafka.weka.utils.JsonToCsv;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Service
@Slf4j
public class JsonToArffService {
    @Value("${json.path}")
    private String jsonPath;

    @Value("${csv.path}")
    private String csvPath;
    @Value("${arff.path}")
    private String arffData;
    private ObjectMapper mapper;

    public String convert(Collection<BatchGeoData> batchGeoData) {
        mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {

            writer.writeValue(new File("src/main/java/com/example/demokafka/files/data.json"), batchGeoData);
            JsonToCsv.createCsv("src/main/java/com/example/demokafka/files/data.json");
            CsvArffConverter.convertCsvToArff("src/main/java/com/example/demokafka/files/dataFromCsv.csv", "src/main/java/com/example/demokafka/files/dataArff.arff");
        }
        catch (IOException e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "src/main/java/com/example/demokafka/files/dataArff.arff";
    }

}
