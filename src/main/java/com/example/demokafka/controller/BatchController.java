package com.example.demokafka.controller;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    ObjectMapper mapper;
    @Autowired
    BatchHilOutService batchHilOutService;
    @Autowired
    BatchDBSCANService batchDBSCANService;
    @Autowired
    BatchGaussBasedService batchGaussBasedService;
    @Autowired
    BatchIsolationForestService batchIsolationForestService;
    @Autowired
    BatchLOFService batchLOFService;

    @PostMapping("/HilOut")
    public List<BatchGeoData> analyzeHilOut(@RequestBody BatchGeoData[] batch) throws Exception {
        return batchHilOutService.analyze(batch);
    }

    @PostMapping("/DBSCAN")
    public List<BatchGeoData> analyzeDBSCAN(@RequestBody BatchGeoData[] batch) throws Exception {
        return batchDBSCANService.analyze(batch);

    }
    @PostMapping("/LOF")
    public List<BatchGeoData> analyzeLOF(@RequestBody BatchGeoData[] batch) throws Exception {
        return batchLOFService.analyze(batch);
    }
    @PostMapping("/IsolationForest")
    public List<BatchGeoData> analyzeIsolationForest(@RequestBody BatchGeoData[] batch) throws Exception {
        return batchIsolationForestService.analyze(batch);
    }
    @PostMapping("/Gauss")
    public List<BatchGeoData> analyzeGauss(@RequestBody BatchGeoData[] batch) throws Exception {
        return batchGaussBasedService.analyze(batch);
    }
}
