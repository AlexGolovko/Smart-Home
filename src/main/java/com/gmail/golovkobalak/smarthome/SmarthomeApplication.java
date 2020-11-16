package com.gmail.golovkobalak.smarthome;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class SmarthomeApplication {


    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(SmarthomeApplication.class, args);
        final Runtime runtime = Runtime.getRuntime();
        log.info("TotalMemory,mb:" + runtime.totalMemory()*0.000001);
        log.info("FreeMemory,mb:" + runtime.freeMemory()*0.000001);
        log.info("MaxMemory,mb:" + runtime.maxMemory()*0.000001);
        final Runner runner = context.getBean(Runner.class);
        runner.run();
    }

}
