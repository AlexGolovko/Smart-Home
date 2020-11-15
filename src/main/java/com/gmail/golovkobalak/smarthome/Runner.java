package com.gmail.golovkobalak.smarthome;

import com.gmail.golovkobalak.smarthome.cashflow.Louis;
import com.gmail.golovkobalak.smarthome.microclimat.SensorSubscriber;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

@Service
public class Runner {

    private Louis cashFlowBot;
    private SensorSubscriber sensorSubscriber;

    public Runner(Louis cashFlowBot, SensorSubscriber sensorSubscriber) {
        this.cashFlowBot = cashFlowBot;
        this.sensorSubscriber = sensorSubscriber;
    }

    public void run() {
        cashFlowBot.run();
        try {
            sensorSubscriber.run();
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
