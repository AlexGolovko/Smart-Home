package com.gmail.golovkobalak.smarthome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SmarthomeApplication {


    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(SmarthomeApplication.class, args);
        final Runner runner = context.getBean(Runner.class);
        runner.run();
    }

}
