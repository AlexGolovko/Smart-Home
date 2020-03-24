package com.golovkobalak.smarthomebot.service;

import com.golovkobalak.smarthomebot.bot.Bot;
import com.golovkobalak.smarthomebot.bot.TelegramBotImpl;
import com.golovkobalak.smarthomebot.data.Measure;
import com.pengrad.telegrambot.UpdatesListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.POST;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AlarmServiceImpl implements AlarmService {
    private static final Logger logger = LoggerFactory.getLogger(AlarmServiceImpl.class);

    private Bot<Measure> bot;

    private Map<String, Integer> boundaries;

    private Map<String, String> measures;

    {
        boundaries = new HashMap<>();
        boundaries.put("sensors/temperature", 50);
        boundaries.put("sensors/humidity", 70);
        boundaries.put("sensors/fire", 1);
        boundaries.put("sensors/smoke", 4000);//we will die
    }

    @Autowired
    public AlarmServiceImpl(Bot<Measure> bot) {
        this.bot = bot;
    }

    @PostConstruct
    private void init() {
        measures = new ConcurrentHashMap<>();
        bot.setUpdateHandler(updates -> {
            updates.forEach(update -> {
                if (update.message() != null && "/check".equalsIgnoreCase(update.message().text())) {
                    Measure measure = new Measure();
                    measure.setMeasures(measures);
                    bot.sendAlarmMessage(measure);
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    @Override
    public void handle(Measure measure) {
        logger.info(measure.toString());
        updateMeasures(measure);
        boolean alarm = measures.entrySet().stream().anyMatch(entry -> {
            if (boundaries.containsKey(entry.getKey()))
                return boundaries.get(entry.getKey()) <= Integer.parseInt(entry.getValue());
            return false;
        });
        if (alarm)
            bot.sendAlarmMessage(measure);

    }

    private synchronized void updateMeasures(Measure measure) {
        measures.clear();
        measures.putAll(measure.getMeasures());
    }
}
