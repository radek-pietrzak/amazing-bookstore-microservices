package com.productservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


@Configuration
// TODO check if it is necessary and delete if not
//@EnableMongoRepositories(basePackages = "com.productservice.repository")
public class MongoDBConfig {
    @Bean
    @Profile("production")
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString(getEnvProperty("CONNECTION_STRING"));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    @Profile("production")
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), getEnvProperty("MONGO_DB"));
    }

    private String getEnvProperty(String property) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("product-service/src/main/resources/env.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return props.getProperty(property);
    }
}
