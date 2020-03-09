package com.golovkobalak.smarthome.model;

import org.eclipse.paho.client.mqttv3.MqttCallback;

public interface Subscriber {
    public Subscriber subscribe(String topic);
}
