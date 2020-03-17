package com.golovkobalak.smarthomebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class SmarthomebotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(SmarthomebotApplication.class, args);
    }

}
