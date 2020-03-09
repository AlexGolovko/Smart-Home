package com.golovkobalak.smarthome.model;

import joptsimple.internal.Strings;
import lombok.Data;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jni.Time;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.objectweb.asm.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;

@Data
@Component
@Scope("prototype")
public class CloudSubscriber implements Subscriber, MqttCallback {

    private  Handler handler;

    private MqttClient client;

    private String topic;
    private static Log logger = LogFactory.getLog(CloudSubscriber.class);


    private CloudSubscriber() {
    }

    @Override
    public Subscriber subscribe(String topic) {
        this.topic = topic;
        Properties mqttProperties = new Properties();
        String uri = Strings.EMPTY;
        String username = Strings.EMPTY;
        String pwd = Strings.EMPTY;
        String clientId = Strings.EMPTY;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            mqttProperties.load(classLoader.getResourceAsStream("mqtt.properties"));
            username = String.valueOf(mqttProperties.get("mqtt.username"));
            pwd = String.valueOf(mqttProperties.get("mqtt.password"));
            clientId = String.valueOf(mqttProperties.get("mqtt.clientid"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(pwd.toCharArray());
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        uri = String.format("tcp://%s:%d", mqttProperties.getProperty("mqtt.host"), Integer.parseInt(mqttProperties.getProperty("mqtt.port")));
        try {
            this.client = new MqttClient(uri, clientId);
            this.client.connect(options);
            this.client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        this.client.setCallback(this);
        return this;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);
        this.client.publish(this.topic, message); // Blocking publish
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        logger.info(topic + "\n" + message.toString());
        handler.handle(topic, message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    @Autowired
    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
