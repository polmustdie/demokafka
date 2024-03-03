package com.example.demokafka.kafka;

import com.example.demokafka.model.*;
import com.example.demokafka.service.*;
import com.example.demokafka.weka.Algo;
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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class KafkaReader {
    private KafkaProperties props;
    private String subscribeString;
    private Properties properties;
    private KafkaConsumer<String, String> consumer;
    ObjectMapper mapper = new ObjectMapper();
    private HashMap<Integer, BatchGeoData> map;
    GeoService geoService;
    List<GeoDataFlag> data = new ArrayList<>();
    ArrayList<Object> constants;
    int mode;

    public KafkaReader(KafkaProperties props, GeoService geoService, KafkaPropertiesAndMode propertiesAndMode) {
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

    }

    public void processing() {
        KafkaWriter kafkaWriter = new KafkaWriter(props);
        GeoData geoDataFromKafka;
        try {
            consumer.subscribe(Collections.singleton(props.getTopic()));
            ConsumerRecords<String, String> records;
            while (!Thread.interrupted()) {
                records = consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    for (ConsumerRecord<String, String> consumerRecord : records) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        geoDataFromKafka = objectMapper.readValue(consumerRecord.value(), GeoData.class);

                        log.info("Message {} read from topic {}", consumerRecord.value(), props.getTopic());
                        geoService.saveClick(geoDataFromKafka);
                        log.info("Successfully added point {} to points clickhouse table", geoDataFromKafka);
//                        kafkaWriter.processing(consumerRecord.value());
                    }
                }

            }
        } catch (Exception e) {
            log.info("Exception caught at kafkaReader processing");
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

        timer.schedule(task, 0, 1000L * 20);

    }

    public void analyze(){
        geoService.updateIsNewField(data, true);

        Collection<BatchGeoData> values = new ArrayList<>();
        BatchGeoData batchData;
        Date date;
        List<BatchGeoData> nodes;
        BatchAlgoService service;
        while (true) {

            if (data.size()>=10) {
                for (GeoDataFlag record : data) {
                    log.info("Record from clickhouse db", record.toString());
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(record.getTimestamp());
                        batchData = new BatchGeoData(date, (double) record.getLongitude(), (double) record.getLatitude(), record.getFlag());
                        values.add(batchData);
                    } catch (ParseException e) {
                    throw new RuntimeException(e);
                    }
                }
                BatchInfoAndData batch = new BatchInfoAndData(constants, values);
                switch (mode){
                    case 1:
                        service = new BatchDBSCANService();
                        break;
                    case 2:
                        service = new BatchGaussBasedService();
                        break;
                    case 3:
                        service = new BatchHilOutService();
                        break;
                    case 4:
                        service = new BatchIsolationForestService();
                        break;
                    default:
                        service = new BatchLOFService();

                }
                nodes = service.analyze(batch);
                System.out.println("IAKJKJBKJWNKLWJBWJLWBL");
                geoService.updateIsNewField(data, false);
                break;
//;                        if (map.size() >= 15) {
//                            Collection<BatchGeoData> values = map.values();
//
//                            BatchInfoAndData batch = new BatchInfoAndData(constants, values);
//                            nodes = dbscanService.analyze(batch);
//                            map.clear();
//                            for (int i = 0; i < nodes.size(); i++) {
//                                System.out.println(nodes.get(i));
//                            }
//                        } else {
//                            map.put(j, data);
//                            j = j+1;
//                        }
                }
            }


        }



//
//    public void processing(ArrayList<Object> constants, int mode) {
//        KafkaWriter kafkaWriter = new KafkaWriter(props);
//        BatchGeoData data;
//        map = new HashMap();
//        List<BatchGeoData> nodes = null;
//        consumer.subscribe(Collections.singleton(props.getTopic()));
//        ConsumerRecords<String, String> records;
//        int j = 1;
//        while (true) {
//            while (!Thread.interrupted()) {
//                records = consumer.poll(Duration.ofMillis(100));
//                if (!records.isEmpty()) {
//                    for (ConsumerRecord<String, String> consumerRecord : records) {
//                        log.info("Message {} read from topic {}", consumerRecord.value(), props.getTopic());
//                        try {
//                            data = mapper.readValue(consumerRecord.value(), BatchGeoData.class);
//                            if (map.size() >= 15) {
//                                Collection<BatchGeoData> values = map.values();
//
//                                BatchInfoAndData batch = new BatchInfoAndData(constants, values);
//                                nodes = dbscanService.analyze(batch);
//                                map.clear();
//                                for (int i = 0; i < nodes.size(); i++) {
//                                    System.out.println(nodes.get(i));
//                                }
//                            } else {
//                                map.put(j, data);
//                                j = j+1;
//                            }
//                        }
//                        catch (JsonProcessingException ex) {
//                            System.out.println("JsonProcessingException caught" + ex.getMessage());
//                        }
//                    }
//                }
//
//            }
//        }
//
//    }
}