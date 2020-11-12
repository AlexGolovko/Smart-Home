package com.gmail.golovkobalak.smarthome.cashflow.repo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan
@PropertySource("classpath:mongo.properties")
@EnableMongoRepositories
public class ExpenseRepoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongo.url}")
    private String url;
    @Value("${mongo.username}")
    private String username;
    @Value("${mongo.dbname}")
    private String dbName;
    @Value("${mongo.pwd}")
    private String pwd;

    @Override
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.createCredential(username, getDatabaseName(), pwd.toCharArray());
        MongoClientSettings settings = MongoClientSettings.builder().credential(credential).applyConnectionString(new ConnectionString(url)).build();
        return MongoClients.create(settings);
    }


    @Override
    protected String getDatabaseName() {
        return dbName;
    }
}
