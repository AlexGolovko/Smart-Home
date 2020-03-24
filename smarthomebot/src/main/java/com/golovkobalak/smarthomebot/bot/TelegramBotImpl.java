package com.golovkobalak.smarthomebot.bot;

import com.golovkobalak.smarthomebot.data.Measure;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.generics.UpdatesHandler;

import javax.annotation.PostConstruct;

@Service
@PropertySource("classpath:bot.properties")
@Log
public class TelegramBotImpl implements Bot<Measure> {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotImpl.class);

    @Value("${bot.token}")
    private String token;

    @Value("${bot.name}")
    private String name;

    @Value("${bot.chatId}")
    private long chatId;

    private TelegramBot bot;

    @PostConstruct
    private void init() {
        bot = new TelegramBot(token);
    }

    public void sendAlarmMessage(Measure measure) {
        logger.info(measure.toString());
        bot.execute(new SendMessage(chatId, "What again:" + measure.toJson()));
    }

    public void setUpdateHandler(UpdatesListener listener) {
        bot.setUpdatesListener(listener);
    }

}
