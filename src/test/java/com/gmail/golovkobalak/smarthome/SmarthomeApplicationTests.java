package com.gmail.golovkobalak.smarthome;

import com.gmail.golovkobalak.smarthome.cashflow.Louis;
import com.gmail.golovkobalak.smarthome.microclimat.SensorSubscriber;
import com.gmail.golovkobalak.smarthome.microclimat.repo.Measure;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SmarthomeApplicationTests {

    @Autowired
    Louis louis;
    @Autowired
    SensorSubscriber sensorSubscriber;

    @Test
    void contextLoads() {
        assertNotNull(louis);
        assertNotNull(sensorSubscriber);
    }

    @Test
    void gsonTest() {
        final Gson gson = new Gson();
        final Measure measure = gson.fromJson("{\"date\":\"2020.11.16 18:32:53\", \"temperatura\":\"26\", \"humidity\":\"42\", \"fire\":\"0\", \"smoke\":\"0\"}", Measure.class);
        System.out.println(measure);
    }
}
