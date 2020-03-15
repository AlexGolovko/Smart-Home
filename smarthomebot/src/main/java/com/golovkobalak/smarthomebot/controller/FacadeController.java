package com.golovkobalak.smarthomebot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@PropertySource("classpath:facade.properties")
@RestController
public class FacadeController {


    private final String url;

    private Logger logger = LoggerFactory.getLogger(FacadeController.class);

    public FacadeController(@Value("${facade.url}")String url) {
        this.url = url;
    }

    @GetMapping()
    private String getMeasure() {
        logger.info("GET MEASURE");
        return "GET MEASURE";
    }

    @PostMapping("/facade")
    private String postMeasure() {
        logger.info("POST MEASURE");

        return "POST MEASURE";
    }
}
