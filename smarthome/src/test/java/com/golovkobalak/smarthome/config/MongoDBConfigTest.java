package com.golovkobalak.smarthome.config;

import com.golovkobalak.smarthome.repo.MongoDBConfig;
import com.mongodb.client.MongoClient;
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
    }
}