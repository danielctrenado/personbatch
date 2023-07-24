package com.danielctrenado.spring.batch.personbatch.service;

import com.danielctrenado.spring.batch.personbatch.integration.db.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public void sendMessage(Person message) {
        this.kafkaTemplate.send(topic, message);
    }
}
