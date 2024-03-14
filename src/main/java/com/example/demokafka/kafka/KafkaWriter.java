package com.example.demokafka.kafka;


import com.example.demokafka.model.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.UUID;


@Slf4j
public class KafkaWriter{
    KafkaProperties props;
    KafkaProducer<String, String> kafkaProducer;
    public KafkaWriter(KafkaProperties props) {
        this.props = props;
        kafkaProducer = new KafkaProducer<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers(),
                ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString()
        ),
                new StringSerializer(),
                new StringSerializer()
        );

    }

    public void processing(String message) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                props.getTopic_out(),
                message);
        kafkaProducer.send(producerRecord);
        log.info("Message {} sent to topic {}", message, props.getTopic_out());
    }
}
