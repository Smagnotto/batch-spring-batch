package br.com.kenlo.digitalrentmigrationperson.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BatchDbConfiguration {
    public static final String BATCH_DATABASE = "BATCH-DATABASE";

    @Value("${batch.datasource.url}")
    private String url;
    @Value("${batch.datasource.username}")
    private String username;
    @Value("${batch.datasource.password}")
    private String password;


    @Primary
    @Bean(name = BATCH_DATABASE, destroyMethod = "close")
    public DataSource batchDatabase() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.setMinimumIdle(2);
        dataSource.setMaximumPoolSize(20);
        dataSource.setIdleTimeout(120000);
        dataSource.setConnectionTimeout(300000);
        dataSource.setLeakDetectionThreshold(300000);
        
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setConnectionInitSql("SELECT 1");
        dataSource.setPoolName("h2-batch-migration-people");

        return dataSource;
    }
}
