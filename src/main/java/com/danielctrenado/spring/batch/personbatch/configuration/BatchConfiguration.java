package com.danielctrenado.spring.batch.personbatch.configuration;

import com.danielctrenado.spring.batch.personbatch.batch.processor.PersonItemProcessor;
import com.danielctrenado.spring.batch.personbatch.integration.db.Person;
import com.danielctrenado.spring.batch.personbatch.integration.file.PersonFieldSetMapper;
import com.danielctrenado.spring.batch.personbatch.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProducerService producerService;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Person, Person>chunk(500)
                .reader(personItemReader())
                .processor(new PersonItemProcessor(this.producerService))
                .writer(personItemWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<Person> personItemReader() {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("input/persons_1000000.csv"));

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "firstName", "lastName", "birthdate", "nationality", "sex", "ssn",
                "email", "cellular", "address1", "address2", "state", "city", "zip");

        DefaultLineMapper<Person> personLineMapper = new DefaultLineMapper<>();
        personLineMapper.setLineTokenizer(tokenizer);
        personLineMapper.setFieldSetMapper(new PersonFieldSetMapper());
        personLineMapper.afterPropertiesSet();

        reader.setLineMapper(personLineMapper);
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<Person> personItemWriter() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();

        writer.setDataSource(this.dataSource);
        writer.setSql("insert into PERSON values(:id, :firstName, :lastName, :birthDate, :nationality, :sex, :ssn" +
                ", :email, :cellular, :address1, :address2, :state, :city, :zip)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.afterPropertiesSet();

        return writer;
    }

}
