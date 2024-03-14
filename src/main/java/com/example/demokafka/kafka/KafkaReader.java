package com.example.demokafka.kafka;

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
    private KafkaWriter kafkaWriter;
    private PostgreRepository postgreRepository;

    public KafkaReader(KafkaProperties props, GeoService geoService, KafkaPropertiesAndMode propertiesAndMode,
                       PostgreRepository postgreRepository) {
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
        this.postgreRepository = postgreRepository;
    }

    public void processing() {
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

    public void analyze() {
        ObjectMapper objectMapper = new ObjectMapper();
        KafkaWriter kafkaWriter = new KafkaWriter(props);

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
                        e.printStackTrace();
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
                try {
                    nodes = service.analyze(batch);
                    System.out.println("IAKJKJBKJWNKLWJBWJLWBL");
//                    geoService.updateIsNewField(data, false);
                    data.clear();

                    for (BatchGeoData node :nodes){
                        try {
                            kafkaWriter.processing(mapper.writeValueAsString(node));
                            BatchGeoDataToPostgres dataToPostgres = new BatchGeoDataToPostgres(node.getDate(), node.getX(), node.getY(),
                                    node.getFlag(), 1, 1);
                            postgreRepository.save(dataToPostgres);
                        }
                        catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}