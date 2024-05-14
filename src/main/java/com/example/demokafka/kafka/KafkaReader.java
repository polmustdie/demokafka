package com.example.demokafka.kafka;

import com.example.demokafka.config.ApplicationProperties;
import com.example.demokafka.model.*;
import com.example.demokafka.repository.PostgreRepository;
import com.example.demokafka.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
@Service
public class KafkaReader {
    private ApplicationProperties applicationProperties;
    private KafkaProperties props;
    private String subscribeString;
    private Properties properties;
    private KafkaConsumer<String, String> consumer;
    private ObjectMapper mapper = new ObjectMapper();
    private HashMap<Integer, BatchGeoData> map;
    private GeoService geoService;
    private List<GeoDataFlag> data = new ArrayList<>();
    private ArrayList<Object> constants;
    private int mode;
    private int windowSize;
    private KafkaWriter kafkaWriter;
    private PostgreRepository postgreRepository;
    private ConverterService converter;

    public KafkaReader(KafkaProperties props, GeoService geoService, KafkaPropertiesAndMode propertiesAndMode,
                       PostgreRepository postgreRepository, ApplicationProperties applicationProperties, ConverterService converter) {
        this.props = props;
        properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "tc-" + UUID.randomUUID());
        consumer = new KafkaConsumer<>(properties);
        this.geoService = geoService;
        this.constants = propertiesAndMode.getConstants();
        this.mode = propertiesAndMode.getMode();
        this.windowSize = propertiesAndMode.getWindowSize();
        this.postgreRepository = postgreRepository;
        kafkaWriter = new KafkaWriter(props);
        this.applicationProperties = applicationProperties;
        this.converter = converter;
    }

    public void processing() {
        GeoDataFlag geoDataFlag;
        try {
            consumer.subscribe(Collections.singleton(props.getTopic()));
            ConsumerRecords<String, String> records;
            while (!Thread.interrupted()) {
                records = consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    for (ConsumerRecord<String, String> consumerRecord : records) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        geoDataFlag = objectMapper.readValue(consumerRecord.value(), GeoDataFlag.class);
                        log.info("Message {} read from topic {}", consumerRecord.value(), props.getTopic());
                        geoService.saveClickGeo(geoDataFlag);
                        log.info("Successfully added point {} to geo_points clickhouse table", geoDataFlag);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void readFromDb(){
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("timer task is called");
                data = geoService.getGeoDataClickFlag();
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task, 0, 1000L * applicationProperties.getReadFromDbSeconds());
    }

    public void analyze() {
        log.info("I AM HERE");
        geoService.updateIsNewField(data, true);
        ArrayList<BatchGeoData> values = new ArrayList<>();
        BatchGeoData batchData;
        Date date;
        List<BatchGeoData> nodes;
        BatchAlgoService service;
        // выход из цикла
        while (true) {
            if (data.size() >= windowSize) {
                BatchInfoAndData batch = converter.convert(data, constants);
                service = switch (mode) {
                    case 1 -> new BatchDBSCANService();
                    case 2 -> new BatchGaussBasedService();
                    case 3 -> new BatchHilOutService();
                    case 4 -> new BatchIsolationForestService();
                    default -> new BatchLOFService();
                };
                try {
                    nodes = service.analyze(batch);
                    geoService.updateIsNewField(data, false);
                    data.clear();
                    batch.getData().clear();
                    sendAndSaveData(nodes);
                } catch (ParseException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    private void sendAndSaveData(List<BatchGeoData> nodes) {
        BatchGeoDataToPostgres dataToPostgres;
        for (BatchGeoData node : nodes){
            try {
                kafkaWriter.processing(mapper.writeValueAsString(node));
                dataToPostgres = new BatchGeoDataToPostgres(node.getDate(), node.getX(), node.getY(),
                        node.getFlag(), node.getPointId(), node.getUserId());
                postgreRepository.save(dataToPostgres);
            }
            catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        }
        log.info("Data was successfully saved to postgres");
    }
}