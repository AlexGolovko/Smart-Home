package com.golovkobalak.smarthome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SmarthomeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SmarthomeApplication.class, args);
        Runner runner = context.getBean(Runner.class);
        runner.run();
    }
}
