package com.golovkobalak.smarthome.model;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jni.Time;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;


public class CloudSubscriber implements MqttCallback {
    private MqttClient client;
    private Log logger = LogFactory.getLog(this.getClass());

    public static CloudSubscriber newCloudSubscriber(String topic) throws MqttException {
        CloudSubscriber subscriber = new CloudSubscriber();
        Properties mqttProperties = new Properties();
        String uri;
        String username = "";
        String pwd = "";
        String clientId = "";
        String host = "";
        try {
            mqttProperties.load(new StringReader("mqtt.properties"));
            uri = String.valueOf(mqttProperties.get("mqtt.uri"));
            username = String.valueOf(mqttProperties.get("mqtt.username"));
            pwd = String.valueOf(mqttProperties.get("mqtt.password"));
            clientId = String.valueOf(mqttProperties.get("mqtt.clientid"));
            host = String.valueOf(mqttProperties.get("mqtt.host"));


        } catch (IOException e) {
            e.printStackTrace();
        }
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(pwd.toCharArray());

        subscriber.client = new MqttClient(host, clientId, new MemoryPersistence());
        subscriber.client.setCallback(subscriber);
        subscriber.client.connect(options);
        subscriber.client.subscribe(topic);
        return subscriber;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        logger.info(topic + "\n" + Arrays.toString(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
