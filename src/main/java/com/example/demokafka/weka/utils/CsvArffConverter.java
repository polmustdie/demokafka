package com.example.demokafka.weka.utils;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        }
        catch(IOException e){
            System.out.println("Caught IOException");
        }

    }
}
