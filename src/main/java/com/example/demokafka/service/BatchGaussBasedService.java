package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchInfoAndData;
import com.example.demokafka.weka.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchGaussBasedService extends BatchAlgoService{
    public List<BatchGeoData> analyze(BatchInfoAndData batchGeoData) throws ParseException {

        ArrayList<Object> constants = batchGeoData.getConstants();
        String path = jsonToArffService.convert(batchGeoData.getData());
        GaussBased gb = new GaussBased(path, constants);
        gb.showResults(gb.getNodeset());
        return gb.getNodes();


    }
}
