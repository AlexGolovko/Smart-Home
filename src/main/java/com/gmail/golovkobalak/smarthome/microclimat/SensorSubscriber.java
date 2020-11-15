package com.gmail.golovkobalak.smarthome.microclimat;

import com.gmail.golovkobalak.smarthome.conroller.MainController;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SensorSubscriber {
    private IMqttClient mqttClient;
    private MqttConfiguration mqttConfiguration;

    public SensorSubscriber(IMqttClient mqttClient, MqttConfiguration mqttConfiguration) {
        this.mqttClient = mqttClient;
        this.mqttConfiguration = mqttConfiguration;
    }

    public void run() throws MqttException, InterruptedException {
        log.info("SensorSubscriber run");
        while (!mqttClient.isConnected()) {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
            log.debug("wait startup the broker");
        }
        CountDownLatch receivedSignal = new CountDownLatch(10);
        mqttClient.subscribe(mqttConfiguration.sensorsTopic, (topic, msg) -> {

            byte[] payload = msg.getPayload();
            // ... payload handling omitted
            final String measure = new String(payload);
            log.debug("MSG: " + measure);
            MainController.measures.add(measure);
            receivedSignal.countDown();
        });
        receivedSignal.await(1, TimeUnit.MINUTES);
    }
}
