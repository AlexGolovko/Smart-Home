package com.golovkobalak.smarthomebot.service;

import com.golovkobalak.smarthomebot.bot.Bot;
import com.golovkobalak.smarthomebot.bot.TelegramBotImpl;
import com.golovkobalak.smarthomebot.data.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AlarmServiceImpl implements AlarmService {
    @Autowired
    private Bot bot;
    private Map<String, Integer> boundaries;


    {
        boundaries = new HashMap<>();
        boundaries.put("", 1);
    }

    @Override
    public void handle(Measure measure) {
        Map<String, String> measures = measure.getMeasures();
        boolean alarm = measures.entrySet().stream().anyMatch(entry -> {
            return boundaries.get(entry.getKey()) > Integer.parseInt(entry.getValue());
        });
        if (alarm) {
         bot.sendAlarmMessage(measure);
        }
    }
}
