package com.danielctrenado.spring.batch.personbatch.batch.processor;

import com.danielctrenado.spring.batch.personbatch.integration.db.Person;
import com.danielctrenado.spring.batch.personbatch.service.ProducerService;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private final ProducerService producerService;

    public PersonItemProcessor(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public Person process(Person item) throws Exception {
        producerService.sendMessage(item);
        return item;
    }

}
