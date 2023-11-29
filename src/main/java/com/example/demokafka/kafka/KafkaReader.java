package com.example.demokafka.kafka;

import com.example.demokafka.model.GeoData;
import com.example.demokafka.model.KafkaProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;


import java.time.Duration;
import java.util.*;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class KafkaReader {
    private KafkaProperties props;
    private String subscribeString;
    private long updateIntervalSec;
    private Properties properties;
    private KafkaConsumer<String, String> consumer;

    public KafkaReader(KafkaProperties props) {
        this.props = props;
        properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "tc-" + UUID.randomUUID());
        consumer = new KafkaConsumer<>(properties);

    }

    public void processing() {
        KafkaWriter kafkaWriter = new KafkaWriter(props);
//        MyRuleProcessor processor = new MyRuleProcessor(config);
//        db = new MyDbReader(config.getConfig("db"));
//        rules = db.readRulesFromDB();
//        updateIntervalSec = config.getConfig("application").getInt("updateIntervalSec");
//        TimerTask task = new TimerTask() {
//            public void run() {
//                rules = db.readRulesFromDB();
//                for (Rule r :
//                        rules) {
//                    log.debug(r.toString());
//                }
//            }
//        };
//        Timer timer = new Timer(true);
//        timer.schedule(task, 0, 1000 * updateIntervalSec);

        try {
            consumer.subscribe(Collections.singleton(props.getTopic()));
            ConsumerRecords<String, String> records;
            while (!Thread.interrupted()) {
                records = consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    for (ConsumerRecord<String, String> consumerRecord : records) {

                        log.info("Message {} read from topic {}", consumerRecord.value(), props.getTopic());
//                        message = new GeoData(consumerRecord.value());
//                        log.debug("check: " + message.getValue());
//                        if (message.isDeduplicationState()) {
                        kafkaWriter.processing(consumerRecord.value());


                    }
                }

            }
        } catch (Exception e) {
            log.info("Exception caught at kafkaReader processing");
        }

    }
}