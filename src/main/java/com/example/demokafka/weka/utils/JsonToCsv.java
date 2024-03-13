package com.example.demokafka.weka.utils;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
//"src/main/resources/data.json"
public class JsonToCsv {
    private JsonToCsv() {
        throw new IllegalStateException("Utility class");
    }

    public static void createCsv(String path) throws IOException {

        JsonNode jsonNode = new ObjectMapper().readTree(new File(path));
//        JsonNode data = jsonNode.get("data");

        CsvSchema.Builder builder = CsvSchema.builder()
                .addColumn("date")
                .addColumn("x")
                .addColumn("y")
                .addColumn("flag");
//                .addColumn("id")
//                .addColumn("user_id");

        CsvSchema csvSchema = builder.build().withHeader().withoutQuoteChar();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.configure(Feature.IGNORE_UNKNOWN, true);
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(new File("src/main/resources/dataFromCsv.csv"), jsonNode);
    }
}