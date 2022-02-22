package br.com.kenlo.digitalrentmigrationperson.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class BatchConfigurer {

    @Autowired
    @Qualifier(value = BatchDbConfiguration.BATCH_DATABASE)
    public DataSource datasource;

    @Bean
    public DefaultBatchConfigurer configurer() {
        return new DefaultBatchConfigurer(datasource);
    }

    @Bean(name = "batch-repositorio")
    public JobRepository getJobRepository() {
        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
        factoryBean.setDataSource(this.datasource);
        factoryBean.setTransactionManager(new DataSourceTransactionManager(datasource));
        factoryBean.setTablePrefix("BATCH_");
        factoryBean.setIsolationLevelForCreate("PROPAGATION_REQUIRED");
        try {
            factoryBean.afterPropertiesSet();
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BatchConfigurationException(e);
        }
    }
}
