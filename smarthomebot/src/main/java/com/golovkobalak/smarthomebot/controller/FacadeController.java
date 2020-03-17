package com.golovkobalak.smarthomebot.controller;

import com.golovkobalak.smarthomebot.data.Measure;
import com.golovkobalak.smarthomebot.service.AlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@PropertySource("classpath:facade.properties")
@RestController
public class FacadeController {


    @Autowired
    private AlarmService alarmService;

    private Executor executor;

    @PostConstruct
    private void postInit() {
        executor = Executors.newSingleThreadExecutor();
    }

    private final String url = "/facade/measure";

    private Logger logger = LoggerFactory.getLogger(FacadeController.class);

    @PostMapping(url)
    @ResponseStatus(HttpStatus.CREATED)
    private void postMeasure(@RequestBody Measure measure) {
        logger.info(String.valueOf(measure));
        executor.execute(() -> alarmService.handle(measure));
    }
}
