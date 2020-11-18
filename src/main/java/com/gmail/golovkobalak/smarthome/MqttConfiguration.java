package com.gmail.golovkobalak.smarthome;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.UUID;

@Slf4j
@Configuration
@PropertySource("classpath:mqtt.properties")
public class MqttConfiguration {
    @Value("${mqtt.url}")
    public String url;
    @Value("${mqtt.sensortopic}")
    public String sensorsTopic;

    @Bean
    public IMqttClient mqttClient() {
        String publisherId = UUID.randomUUID().toString();
        IMqttClient mqttClient = null;
        try {
            mqttClient = new MqttClient(url, publisherId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            mqttClient.connect(options);
            log.info("mqttClient connected");
        } catch (MqttException e) {
            log.debug("mqttClient", e);
        }
        return mqttClient;
    }
}
