package com.gmail.golovkobalak.smarthome;

import com.gmail.golovkobalak.smarthome.cashflow.repo.CashFlowRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmarthomeApplicationTests {
    @Autowired
    CashFlowRepo cashFlowRepo;

    @Test
    void contextLoads() {

    }

}
