package com.golovkobalak.smarthome.model;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SubscribeHandler implements Handler {

    private static Log logger = LogFactory.getLog(SubscribeHandler.class);

    @Override
    public void handle(String topic, String message) {

    }
}
