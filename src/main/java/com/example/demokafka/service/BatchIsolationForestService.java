package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchInfoAndData;
import com.example.demokafka.weka.IsolationForests;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchIsolationForestService extends BatchAlgoService{
    public List<BatchGeoData> analyze(BatchInfoAndData batch) throws ParseException {
        ArrayList<Object> constants = batch.getConstants();
        String path = jsonToArffService.convert(batch.getData());

        IsolationForests gb = new IsolationForests(path, constants);
        gb.showResults(gb.getNodeset());
        return gb.getNodes();
    }
}
