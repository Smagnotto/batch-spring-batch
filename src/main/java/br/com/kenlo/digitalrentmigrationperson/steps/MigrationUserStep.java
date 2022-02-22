package br.com.kenlo.digitalrentmigrationperson.steps;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import br.com.kenlo.digitalrentmigrationperson.data.destination.Persons;
import br.com.kenlo.digitalrentmigrationperson.data.source.ImobUser;
import br.com.kenlo.digitalrentmigrationperson.config.SqlServerConfiguration;
import br.com.kenlo.digitalrentmigrationperson.mappers.ImobUserRowMapper;
import br.com.kenlo.digitalrentmigrationperson.processors.UserProcessor;

@Configuration
public class MigrationUserStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private final String SELECT_FIELDS = "SELECT tu.id_usuario,"
            + " tu.tx_email,"
            + " tu.tx_telefone,"
            + " tu.tx_celular,"
            + " tu.tx_foto,"
            + " tu.tx_nome_completo,"
            + " tu.tx_cpf,"
            + " tu.tx_creci,"
            + " tu.dt_nascimento,"
            + " tu.fl_ativo,"
            + " tif.id_imobiliaria"
            + " FROM tb_usuarios tu"
            + " INNER JOIN tb_imobiliarias_filiais tif on tif.id_imobiliaria_filial = tu.id_imobiliaria_filial"
            + " INNER JOIN tb_imobiliarias ti on ti.id_imobiliaria = tif.id_imobiliaria"
            + " WHERE ti.id_imobiliaria = ${id_imobiliaria}"
            + " ORDER BY tu.dt_atualizacao DESC";

    @Bean
    @StepScope
    public JdbcCursorItemReader<ImobUser> pagingItemReaderUsers(
            @Value("#{jobParameters['id_imobiliaria']}") Long id_imobiliaria,
            @Qualifier(SqlServerConfiguration.SOURCE_DATABASE) DataSource dataSource) {

        JdbcCursorItemReader<ImobUser> cursorItemReader = new JdbcCursorItemReader<>();

        String sqlQuery = SELECT_FIELDS.replace("${id_imobiliaria}", id_imobiliaria.toString());

        cursorItemReader.setSql(sqlQuery);
        cursorItemReader.setDataSource(dataSource);
        cursorItemReader.setRowMapper(new ImobUserRowMapper());
        return cursorItemReader;
    }

    @Bean
    public UserProcessor processorUsers() {
        return new UserProcessor();
    }

    @Bean
    public MongoItemWriter<Persons> itemWriterUsers(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Persons>()
                .template(mongoTemplate)
                .collection("persons")
                .build();
    }

    @Bean
    public Step stepUsers() {
        return stepBuilderFactory.get("step migration users")
                .<ImobUser, Persons>chunk(100)
                .reader(pagingItemReaderUsers(null, null))
                .processor(processorUsers())
                .writer(itemWriterUsers(null))
                .build();
    }
}
