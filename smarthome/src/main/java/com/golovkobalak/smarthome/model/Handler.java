package com.golovkobalak.smarthome.model;

public interface Handler {
    public void handle(String topic, String message);
}
