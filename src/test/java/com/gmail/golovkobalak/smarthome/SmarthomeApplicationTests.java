package com.gmail.golovkobalak.smarthome;

import com.gmail.golovkobalak.smarthome.cashflow.Louis;
import com.gmail.golovkobalak.smarthome.microclimat.SensorSubscriber;
import com.gmail.golovkobalak.smarthome.microclimat.repo.Measure;
import com.gmail.golovkobalak.smarthome.microclimat.repo.MeasureRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SmarthomeApplicationTests {

    @Autowired
    Louis louis;
    @Autowired
    SensorSubscriber sensorSubscriber;
    @Autowired
    MeasureRepo measureRepo;

    @Test
    void contextLoads() {
        assertNotNull(louis);
        assertNotNull(sensorSubscriber);
    }

    @Test
    void gsonTest() {
        final Gson gson = new GsonBuilder().setDateFormat("YYYY.MM.DD HH:MM:SS").create();
        final Measure measure = gson.fromJson("{\"date\":\"2020.11.16 18:32:53\", \"temperatura\":\"26\", \"humidity\":\"42\", \"fire\":\"0\", \"smoke\":\"0\"}", Measure.class);
        assertNotNull(measure);
    }
}
