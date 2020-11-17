package com.gmail.golovkobalak.smarthome.microclimat;

import com.gmail.golovkobalak.smarthome.MqttConfiguration;
import com.gmail.golovkobalak.smarthome.microclimat.repo.Measure;
import com.gmail.golovkobalak.smarthome.microclimat.repo.MeasureRepo;
import com.gmail.golovkobalak.smarthome.microclimat.validator.AlarmMeasureValidator;
import com.gmail.golovkobalak.smarthome.microclimat.validator.AlarmValidator;
import com.gmail.golovkobalak.smarthome.telegram.bot.Bot;
import com.google.gson.Gson;
import com.pengrad.telegrambot.UpdatesListener;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SensorSubscriber {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Gson GSON = new Gson();
    private IMqttClient mqttClient;
    private MqttConfiguration mqttConfiguration;
    private Bot microClimateBot;
    private AlarmValidator<Measure> alarmMeasureValidator;
    private MeasureRepo measureRepo;
    @Value("${climatebot.chat}")
    private String chatId;
    private UpdatesListener microClimateUpdateListener;

    public SensorSubscriber(IMqttClient mqttClient, MqttConfiguration mqttConfiguration, Bot microClimateBot, AlarmMeasureValidator alarmMeasureValidator, MeasureRepo measureRepo, UpdatesListener microClimateUpdateListener) {
        this.mqttClient = mqttClient;
        this.mqttConfiguration = mqttConfiguration;
        this.microClimateBot = microClimateBot;
        this.alarmMeasureValidator = alarmMeasureValidator;
        this.measureRepo = measureRepo;
        this.microClimateUpdateListener = microClimateUpdateListener;
    }

    public void run() {
        try {
            microClimateBot.setUpdateListener(microClimateUpdateListener);
            runInternal();
        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runInternal() throws MqttException, InterruptedException {
        while (!mqttClient.isConnected()) {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
            log.info("wait startup the broker");
        }
        CountDownLatch receivedSignal = new CountDownLatch(10);
        mqttClient.subscribe(mqttConfiguration.sensorsTopic, (topic, msg) -> {

            final byte[] payload = msg.getPayload();
            // ... payload handling omitted
            //{"date":"2020.11.16 18:32:53", "temperatura":"26", "humidity":"42", "fire":"0", "smoke":"0"}
            final Measure measure = GSON.fromJson(new String(payload), Measure.class);
            executor.execute(() -> measureRepo.save(measure));
            log.info("Measure:" + measure.toString());
            if (!alarmMeasureValidator.validate(measure)) {
                microClimateBot.sendMessage(chatId, measure.toString());
            }
            receivedSignal.countDown();
        });
        receivedSignal.await(1, TimeUnit.MINUTES);
    }
}
