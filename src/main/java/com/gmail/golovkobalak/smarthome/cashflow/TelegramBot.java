package com.gmail.golovkobalak.smarthome.cashflow;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.GetChat;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Lazy
@Component
@PropertySource("classpath:bot.properties")
public class TelegramBot implements Bot {

    private final com.pengrad.telegrambot.TelegramBot bot;

    public TelegramBot(@Value("${bot.token}") String token) {
        bot = new com.pengrad.telegrambot.TelegramBot(token);
    }


    @Override
    public void sendMessage(String chatId, String message) {
        bot.execute(new GetChat(chatId));
        bot.execute(new SendMessage(chatId, message));
    }

    @Override
    public void setUpdateListener(UpdatesListener listener) {
        bot.setUpdatesListener(listener, (response) -> {
            if (response != null) {
                log.info("bad response: " + response.getMessage());
                response.printStackTrace();

            }
            log.info("RESPONSE IS NULL");
            try {
                this.bot.removeGetUpdatesListener();
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                this.setUpdateListener(listener);
            }
        });
    }
}
