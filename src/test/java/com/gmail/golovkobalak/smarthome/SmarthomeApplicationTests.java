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
import org.yaml.snakeyaml.Yaml;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        final Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd HH:mm:ss").create();
        final Measure measure = gson.fromJson("{\"date\":\"2020.11.16 18:32:53\", \"temperatura\":\"26\", \"humidity\":\"42\", \"fire\":\"0\", \"smoke\":\"0\"}", Measure.class);

        measure.setDate(new Date());
        measureRepo.save(measure);
        measure.setDate(new Date());
        measureRepo.save(measure);
        final List<Measure> measures = measureRepo.findAll();
        final Measure measureMax = measures.stream().max(Comparator.comparing(Measure::getDate)).get();
        final Measure top = measureRepo.findTopByOrderByDateDesc();
        assertEquals(measure, top);
        System.out.println(measure);
        System.out.println(top);
        assertEquals(measureMax, top);
    }
}
