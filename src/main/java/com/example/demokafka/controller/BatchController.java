package com.example.demokafka.controller;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchGeoDataToController;
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
    @Autowired
    private ConverterService converterService;

    @PostMapping("/HilOut")
    public List<BatchGeoData> analyzeHilOut(@RequestBody BatchGeoDataToController batch) throws Exception {
        return batchHilOutService.analyze(converterService.convert(batch));
    }

    @PostMapping("/DBSCAN")
    public List<BatchGeoData> analyzeDBSCAN(@RequestBody BatchGeoDataToController batch) throws Exception {
        return batchDBSCANService.analyze(converterService.convert(batch));

    }
    @PostMapping("/LOF")
    public List<BatchGeoData> analyzeLOF(@RequestBody BatchGeoDataToController batch) throws Exception {
        return batchLOFService.analyze(converterService.convert(batch));
    }
    @PostMapping("/IsolationForest")
    public List<BatchGeoData> analyzeIsolationForest(@RequestBody BatchGeoDataToController batch) throws Exception {
        return batchIsolationForestService.analyze(converterService.convert(batch));
    }
    @PostMapping("/Gauss")
    public List<BatchGeoData> analyzeGauss(@RequestBody BatchGeoDataToController batch) throws Exception {
        return batchGaussBasedService.analyze(converterService.convert(batch));
    }
}
