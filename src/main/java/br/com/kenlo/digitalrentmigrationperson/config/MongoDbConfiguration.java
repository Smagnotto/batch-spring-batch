package br.com.kenlo.digitalrentmigrationperson.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoDbConfiguration {

    public static final String DESTINATION_DATABSE = "MONGODB-DATABASE";

    @Value("${mongodb.uri}")
    private String mongodbUri;

    @Bean
    public MongoDatabaseFactory mongoDbFactory() throws Exception {
        return new SimpleMongoClientDatabaseFactory(mongodbUri);
    }

    @Bean(name = DESTINATION_DATABSE)
    public MongoTemplate mongoTemplate() throws Exception {

        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        mappingConverter.afterPropertiesSet();

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), mappingConverter);
        return mongoTemplate;
    }
}
