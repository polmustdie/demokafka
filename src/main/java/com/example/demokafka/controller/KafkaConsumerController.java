package com.example.demokafka.controller;

import com.example.demokafka.kafka.KafkaReader;
import com.example.demokafka.model.KafkaPropertiesAndMode;
import com.example.demokafka.repository.PostgreRepository;
import com.example.demokafka.service.GeoService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    ObjectMapper mapper;
    @Autowired
    GeoService geoService;
    @Autowired
    PostgreRepository postgreRepository;

    @PostMapping("/createSend")
    public void createAndSend(@RequestBody KafkaPropertiesAndMode properties) {
        mapper = new ObjectMapper();
        KafkaReader kafkaReader = new KafkaReader(properties.getProperties(), geoService, properties, postgreRepository);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(kafkaReader::processing);
        executorService.execute(kafkaReader::readFromDb);
        executorService.execute(kafkaReader::analyze);
    }
}
