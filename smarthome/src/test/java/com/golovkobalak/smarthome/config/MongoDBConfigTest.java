package com.golovkobalak.smarthome.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MongoDBConfigTest {
    @Autowired
    private MongoDBConfig mongoDBConfig;

    @Test
    void mongoClient() {
        MongoClient client = mongoDBConfig.mongoClient();
        assertNotNull(client.listDatabaseNames());
        MongoDatabase database = client.getDatabase(mongoDBConfig.getDatabaseName());
        assertNotNull(database);
    }
}