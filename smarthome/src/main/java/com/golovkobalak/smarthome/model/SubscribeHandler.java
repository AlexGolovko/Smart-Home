package com.golovkobalak.smarthome.model;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SubscribeHandler implements Handler {

    private static Log logger = LogFactory.getLog(SubscribeHandler.class);

    private Map<String, String> measures;

    {
        measures = new ConcurrentHashMap();
    }

    @Override
    public void handle(String topic, String message) {
        if (measures.containsKey(topic)) {


        }
        measures.put(topic, message);
    }
}
