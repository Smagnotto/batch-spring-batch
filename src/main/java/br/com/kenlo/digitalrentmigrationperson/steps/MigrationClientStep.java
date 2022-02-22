package br.com.kenlo.digitalrentmigrationperson.steps;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.SqlServerPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import br.com.kenlo.digitalrentmigrationperson.data.destination.Persons;
import br.com.kenlo.digitalrentmigrationperson.data.source.ImobClient;
import br.com.kenlo.digitalrentmigrationperson.config.SqlServerConfiguration;
import br.com.kenlo.digitalrentmigrationperson.mappers.ImobClientRowMapper;
import br.com.kenlo.digitalrentmigrationperson.processors.ClientProcessor;

@Configuration
@EnableBatchProcessing
public class MigrationClientStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private String SELECT_FIELDS = "id_cliente, tx_nome,"
            + " tx_email, tx_rg, tx_cnpj,"
            + " tx_referencia, tx_nacionalidade,"
            + " id_imobiliaria, tx_celular, tx_cpf,"
            + " tx_telefone_residencia, dt_atualizado";

    @Bean
    @StepScope
    public JdbcPagingItemReader<ImobClient> pagingItemReaderClients(
            @Value("#{jobParameters['id_imobiliaria']}") Long id_imobiliaria,
            @Qualifier(SqlServerConfiguration.SOURCE_DATABASE) DataSource dataSource) {

        JdbcPagingItemReader<ImobClient> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(1000);
        reader.setPageSize(1000);
        reader.setRowMapper(new ImobClientRowMapper());
        reader.setQueryProvider(queryProvider(id_imobiliaria));
        return reader;
    }

    private SqlServerPagingQueryProvider queryProvider(Long id_imobiliaria) {
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("dt_atualizado", Order.DESCENDING);

        SqlServerPagingQueryProvider queryProvider = new SqlServerPagingQueryProvider();

        queryProvider.setSelectClause(SELECT_FIELDS);
        queryProvider.setFromClause("tb_clientes");
        queryProvider.setWhereClause("WHERE id_imobiliaria = " + id_imobiliaria);
        queryProvider.setSortKeys(sortKeys);

        return queryProvider;
    }

    @Bean
    public ClientProcessor processorClients() {
        return new ClientProcessor();
    }

    @Bean
    public MongoItemWriter<Persons> itemWriterClients(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Persons>()
                .template(mongoTemplate)
                .collection("persons")
                .build();
    }

    @Bean
    public Step stepClients() {
        return stepBuilderFactory.get("step migration clients")
                .<ImobClient, Persons>chunk(1000)
                .reader(pagingItemReaderClients(null, null))
                .processor(processorClients())
                .writer(itemWriterClients(null))
                .build();
    }
}
