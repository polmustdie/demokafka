package com.example.demokafka.controller;

import com.example.demokafka.kafka.KafkaReader;
import com.example.demokafka.model.KafkaProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import dev.atken.springkafkadynamicconsumer.listener.KafkaListenerContainerManager;
//import dev.atken.springkafkadynamicconsumer.model.KafkaConsumerAssignment;
//import dev.atken.springkafkadynamicconsumer.model.KafkaConsumerRequest;
//import dev.atken.springkafkadynamicconsumer.model.KafkaConsumerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/consumers")
public class KafkaConsumerController {
    ObjectMapper mapper;

    @PostMapping
    public void create(@RequestBody KafkaProperties properties) {
        mapper = new ObjectMapper();
//        KafkaProperties props = mapper.readValue(properties, KafkaProperties.class);
        KafkaReader kafkaReader = new KafkaReader(properties);
        kafkaReader.processing();
    }


//
//    @GetMapping(path="/{listenerId}")
//    public KafkaConsumerResponse get(@PathVariable String listenerId) {
//        return createKafkaConsumerResponse(getListenerContainer(listenerId));
//    }
//
//    @PutMapping(path = "/{listenerId}/activate")
//    public void activate(@PathVariable String listenerId) {
//        MessageListenerContainer listenerContainer = getListenerContainer(listenerId);
//
//        if (listenerContainer.isRunning()) {
//            throw new RuntimeException("Consumer is already running : " + listenerId);
//        }
//
//        listenerContainer.start();
//    }
//
//    @PutMapping(path = "/{listenerId}/pause")
//    public void pause(@PathVariable String listenerId) {
//        MessageListenerContainer listenerContainer = getListenerContainer(listenerId);
//        if (!listenerContainer.isRunning()) {
//            throw new RuntimeException("Consumer is not running: " + listenerId);
//        } else if (listenerContainer.isContainerPaused()) {
//            throw new RuntimeException("Consumer is already paused: " + listenerId);
//        } else if (listenerContainer.isPauseRequested()) {
//            throw new RuntimeException("Consumer pause is already requested: " + listenerId);
//        }
//        listenerContainer.pause();
//    }
//
//    @PutMapping(path = "/{listenerId}/resume")
//    public void resume(@PathVariable String listenerId) {
//        MessageListenerContainer listenerContainer = getListenerContainer(listenerId);
//        if (!listenerContainer.isRunning()) {
//            throw new RuntimeException("Consumer is not running: " + listenerId);
//        } else if (!listenerContainer.isContainerPaused()) {
//            throw new RuntimeException("Consumer is not paused: " + listenerId);
//        }
//        listenerContainer.resume();
//    }
//
//    @PutMapping(path = "stop")
//    public void stopAll() {
//        kafkaListenerContainerManager.listContainers().forEach(container -> stop(container.getListenerId()));
//    }
//
//    @PutMapping(path = "/{listenerId}/stop")
//    public void stop(@PathVariable String listenerId) {
//        MessageListenerContainer listenerContainer = getListenerContainer(listenerId);
//        if (!listenerContainer.isRunning()) {
//            throw new RuntimeException("Consumer is already stopped: " + listenerId);
//        }
//        listenerContainer.stop();
//    }
//
//    @DeleteMapping
//    public void deleteAll() {
//        kafkaListenerContainerManager.listContainers().forEach(container -> delete(container.getListenerId()));
//    }
//
//    @DeleteMapping(path = "{listenerId}")
//    public void delete(@PathVariable String listenerId) {
//        MessageListenerContainer listenerContainer = getListenerContainer(listenerId);
//        listenerContainer.stop();
//        kafkaListenerContainerManager.unregisterListener(listenerId);
//    }
//
//    private MessageListenerContainer getListenerContainer(String listenerId) {
//        Optional<MessageListenerContainer> listenerContainerOpt = kafkaListenerContainerManager.getContainer(listenerId);
//        if (listenerContainerOpt.isEmpty()) {
//            throw new RuntimeException("No such consumer: " + listenerId);
//        }
//
//        return listenerContainerOpt.get();
//    }
//
//    private KafkaConsumerResponse createKafkaConsumerResponse(MessageListenerContainer listenerContainer) {
//        return KafkaConsumerResponse.builder()
//                .groupId(listenerContainer.getGroupId())
//                .listenerId(listenerContainer.getListenerId())
//                .active(listenerContainer.isRunning())
//                .assignments(Optional.ofNullable(listenerContainer.getAssignedPartitions())
//                        .map(topicPartitions -> topicPartitions.stream()
//                                .map(this::createKafkaConsumerAssignmentResponse)
//                                .collect(Collectors.toList()))
//                        .orElse(null))
//                .build();
//    }
//
//    private KafkaConsumerAssignment createKafkaConsumerAssignmentResponse(TopicPartition topicPartition) {
//        return KafkaConsumerAssignment.builder()
//                .topic(topicPartition.topic())
//                .partition(topicPartition.partition())
//                .build();
//    }
}
