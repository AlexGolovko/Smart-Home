package com.gmail.golovkobalak.smarthome;

import com.gmail.golovkobalak.smarthome.cashflow.Louis;
import com.gmail.golovkobalak.smarthome.microclimat.SensorSubscriber;
import com.gmail.golovkobalak.smarthome.microclimat.repo.MeasureRepo;
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

}
