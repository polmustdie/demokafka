package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchInfoAndData;
import com.example.demokafka.weka.LocalOutlierFactor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchLOFService extends BatchAlgoService{
    public List<BatchGeoData> analyze(BatchInfoAndData batch) throws ParseException {

        ArrayList<Object> constants = batch.getConstants();

        String path = jsonToArffService.convert(batch.getData());
        LocalOutlierFactor gb = new LocalOutlierFactor(path, constants);

        gb.showResults(gb.getNodeset());
        return gb.getNodes();
    }
}
