package br.com.kenlo.digitalrentmigrationperson.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlServerConfiguration {
    
    public static final String SOURCE_DATABASE = "SQLSERVER-DATABASE";

    @Value("${sqlserver.datasource.url}")
    private String url;
    @Value("${sqlserver.datasource.username}")
    private String username;
    @Value("${sqlserver.datasource.password}")
    private String password;
    @Value("${sqlserver.datasource.driverClassName}")
    private String driverClassName;


    @Bean(name = SOURCE_DATABASE, destroyMethod = "close")
    public DataSource connectionSqlServer() {

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(20);
        dataSource.setIdleTimeout(30000);
        dataSource.setMaxLifetime(2000000);
        dataSource.setConnectionTimeout(30000);
        
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setPoolName("sql-batch-migration-people");

        return dataSource;
    }
}
