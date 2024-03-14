package com.example.demokafka.weka.utils;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvArffConverter

{
    private CsvArffConverter() {
        throw new IllegalStateException("Utility class");
    }
    public static void convertCsvToArff(String sourcepath,String destpath)
    {
        // load CSV
        try {
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File(sourcepath));
            Instances dataSet = loader.getDataSet();
            // save ARFF
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(destpath))) {
                writer.write(dataSet.toString());
                writer.flush();
            }
            List<String> lines = Files.readAllLines(Path.of(destpath), StandardCharsets.UTF_8);
            lines.set(2, "@attribute date date \"yyyy-MM-dd HH:mm:ss\"");
            Files.write(Path.of(destpath), lines, StandardCharsets.UTF_8);
        }

        catch(IOException e){
            System.out.println("Caught IOException");
        }

    }
}
