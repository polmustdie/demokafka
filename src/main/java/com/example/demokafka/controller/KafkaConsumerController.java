package com.example.demokafka.controller;

import com.example.demokafka.config.ApplicationProperties;
import com.example.demokafka.kafka.KafkaReader;
import com.example.demokafka.model.KafkaPropertiesAndMode;
import com.example.demokafka.repository.PostgreRepository;
import com.example.demokafka.service.ConverterService;
import com.example.demokafka.service.GeoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/consumers")
public class KafkaConsumerController {
    @Autowired
    private GeoService geoService;
    @Autowired
    private PostgreRepository postgreRepository;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private ConverterService converter;

    @PostMapping("/createSend")
    public void createAndSend(@RequestBody KafkaPropertiesAndMode properties) {
        KafkaReader kafkaReader = new KafkaReader(properties.getProperties(), geoService, properties, postgreRepository, applicationProperties, converter);
        Thread analyzeThread = new Thread(kafkaReader::analyze);
        Thread readThread = new Thread(kafkaReader::readFromDb);
        Thread processingThread = new Thread(kafkaReader::processing);
        processingThread.start();
        readThread.start();
        analyzeThread.start();

//        processingThread.start();

//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        executorService.execute(kafkaReader::analyze);
//        executorService.execute(kafkaReader::readFromDb);
    }
}
