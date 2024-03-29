package com.example.demokafka.controller;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchInfoAndData;
import com.example.demokafka.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/batch")
public class BatchController {
    @Autowired
    private BatchHilOutService batchHilOutService;
    @Autowired
    private BatchDBSCANService batchDBSCANService;
    @Autowired
    private BatchGaussBasedService batchGaussBasedService;
    @Autowired
    private BatchIsolationForestService batchIsolationForestService;
    @Autowired
    private BatchLOFService batchLOFService;

    @PostMapping("/HilOut")
    public List<BatchGeoData> analyzeHilOut(@RequestBody BatchInfoAndData batch) throws Exception {
        return batchHilOutService.analyze(batch);
    }

    @PostMapping("/DBSCAN")
    public List<BatchGeoData> analyzeDBSCAN(@RequestBody BatchInfoAndData batch) throws Exception {
        return batchDBSCANService.analyze(batch);

    }
    @PostMapping("/LOF")
    public List<BatchGeoData> analyzeLOF(@RequestBody BatchInfoAndData batch) throws Exception {
        return batchLOFService.analyze(batch);
    }
    @PostMapping("/IsolationForest")
    public List<BatchGeoData> analyzeIsolationForest(@RequestBody BatchInfoAndData batch) throws Exception {
        return batchIsolationForestService.analyze(batch);
    }
    @PostMapping("/Gauss")
    public List<BatchGeoData> analyzeGauss(@RequestBody BatchInfoAndData batch) throws Exception {
        return batchGaussBasedService.analyze(batch);
    }
}
