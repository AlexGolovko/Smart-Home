package com.golovkobalak.smarthome.model;

import lombok.Data;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
@PropertySource("classpath:mqtt.properties")
public class CloudSubscriber implements Subscriber, MqttCallback {

    @Value("${mqtt.username}")
    private String username;
    @Value("${mqtt.password}")
    private String pwd;
    @Value("${mqtt.clientId}")
    private String clientId;
    @Value("${mqtt.host}")
    private String host;
    @Value("${mqtt.port}")
    private String port;

    private Handler handler;

    private MqttClient client;

    private String topic;
    private static Log logger = LogFactory.getLog(CloudSubscriber.class);


    private CloudSubscriber() {
    }

    @Override
    public Subscriber subscribe(String topic) {
        this.topic = topic;
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(pwd.toCharArray());
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        String uri = String.format("tcp://%s:%d", host, Integer.parseInt(port));
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
        cause.printStackTrace();
    }

    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);
        this.client.publish(this.topic, message); // Blocking publish
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        logger.info(Thread.currentThread().getName()+"\t"+topic + "\t" + message.toString());
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
