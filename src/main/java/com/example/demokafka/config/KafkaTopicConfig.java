//package com.example.demokafka.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//
//@Configuration
//public class KafkaTopicConfig {
//
//    @Bean
//    public NewTopic newTopic(){
//        return TopicBuilder.name("anomaly_topic").partitions(3)
//                .build();
//    }
//    @Bean
//    public NewTopic newOutputTopic(){
//        return TopicBuilder.name("anomaly_topic_output").partitions(3)
//                .build();
//    }
//}
