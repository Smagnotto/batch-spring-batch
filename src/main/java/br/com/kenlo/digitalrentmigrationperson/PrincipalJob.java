package br.com.kenlo.digitalrentmigrationperson;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.kenlo.digitalrentmigrationperson.notifications.JobNotification;
import br.com.kenlo.digitalrentmigrationperson.tasklet.ClearAllPersonsTasklet;
import br.com.kenlo.digitalrentmigrationperson.tasklet.GetAllPersonsTasklet;

@Configuration
@EnableBatchProcessing
public class PrincipalJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(JobNotification listener) {
        return jobBuilderFactory.get("principalJob")
                .start(flowGetAllPersonsTasklet())
                .on(FlowExecutionStatus.COMPLETED.getName())
                .to(migrationUsers(null))
                .next(migrationClients(null))
                .on("*")
                .to(flowClearAllPersonsFromMemoryTasklet())
                .build()
                .listener(listener)
                .build();
    }

    @Bean
    public GetAllPersonsTasklet createGetAllPersonsTasklet() {
        return new GetAllPersonsTasklet();
    }

    @Bean
    public Step getAllPersons() {
        return stepBuilderFactory.get("get all persons from database")
                .tasklet(createGetAllPersonsTasklet())
                .build();
    }

    @Bean
    public Flow flowGetAllPersonsTasklet() {
        return new FlowBuilder<SimpleFlow>("tasklet -> get all persons")
                .start(getAllPersons())
                .build();
    }

    @Bean
    public Flow migrationUsers(Step stepUsers) {
        return new FlowBuilder<SimpleFlow>("migration users")
                .start(stepUsers)
                .build();
    }

    @Bean
    public Flow migrationClients(Step stepClients) {
        return new FlowBuilder<SimpleFlow>("migration clients")
                .start(stepClients)
                .build();
    }


    @Bean
    public ClearAllPersonsTasklet createClearAllPersonsTasklet() {
        return new ClearAllPersonsTasklet();
    }

    @Bean
    public Step clearAllPersonsFromMemory() {
        return stepBuilderFactory.get("clear all persons from memory")
                .tasklet(createClearAllPersonsTasklet())
                .build();
    }

    @Bean
    public Flow flowClearAllPersonsFromMemoryTasklet() {
        return new FlowBuilder<SimpleFlow>("tasklet -> clear all persons from memory")
                .start(clearAllPersonsFromMemory())
                .build();
    }

    
}