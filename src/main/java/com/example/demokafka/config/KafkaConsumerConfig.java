//package com.example.demokafka.config;
//
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
////https://medium.com/bliblidotcom-techblog/dynamic-spring-boot-kafka-consumer-af8740f2c703
////https://sid-infinity-yadav.medium.com/airline-data-analysis-with-clickhouse-and-redis-om-c868f3021fd7
//
//@EnableKafka
//@Configuration
//public class KafkaConsumerConfig {
//
//@Bean
//public ConsumerFactory<String, String> consumerFactory()
//{
//
//    // Creating a Map of string-object pairs
//    Map<String, Object> config = new HashMap<>();
//
//    // Adding the Configuration
//    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//            "127.0.0.1:9092");
//    config.put(ConsumerConfig.GROUP_ID_CONFIG,
//            "myGroup");
//    config.put(
//            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//            StringDeserializer.class);
//    config.put(
//            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//            JsonDeserializer.class);
//
//    return new DefaultKafkaConsumerFactory<>(config);
//}
//
//// Creating a Listener
//public ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory() {
//    ConcurrentKafkaListenerContainerFactory<
//            String, String> factory
//            = new ConcurrentKafkaListenerContainerFactory<>();
//    factory.setConsumerFactory(consumerFactory());
//    return factory;
//    }
//}
//
