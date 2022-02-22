package br.com.kenlo.digitalrentmigrationperson.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;

@Configuration
public class BatchConfigurationTest {
    
    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }
}
