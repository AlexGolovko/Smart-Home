package com.gmail.golovkobalak.smarthome;

import com.gmail.golovkobalak.smarthome.cashflow.Louis;
import com.gmail.golovkobalak.smarthome.cashflow.repo.CashFlowRepo;
import com.gmail.golovkobalak.smarthome.cashflow.repo.ExpenseRepoConfig;
import com.gmail.golovkobalak.smarthome.microclimat.MqttConfiguration;
import com.gmail.golovkobalak.smarthome.microclimat.SensorSubscriber;
import org.apache.catalina.core.ApplicationContextFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SmarthomeApplicationTests {

    @MockBean
    List<MongoRepository> repos;
    @Autowired
    Louis louis;
    @Autowired
    SensorSubscriber sensorSubscriber;

    @Test
    void contextLoads() {
        assertNotNull(louis);
        assertNotNull(sensorSubscriber);
    }

}
