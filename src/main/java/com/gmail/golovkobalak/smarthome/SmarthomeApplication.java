package com.gmail.golovkobalak.smarthome;

import com.gmail.golovkobalak.smarthome.cashflow.Louis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SmarthomeApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(SmarthomeApplication.class, args);
        final Louis bean = context.getBean(Louis.class);
        bean.run();
    }

}
