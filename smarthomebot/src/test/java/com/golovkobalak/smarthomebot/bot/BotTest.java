package com.golovkobalak.smarthomebot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLOutput;

@SpringBootTest
@PropertySource("classpath:bot.properties")
class BotTest {

    @Value("${bot.token}")
    private String token;

    @BeforeEach
    private void setUp() {

    }

    @Disabled
    @Test
    public void TelegramAPi() {
        try {
            TelegramBot bot = new TelegramBot(token);
            bot.setUpdatesListener(updates -> {
                updates.forEach(update -> {

                    Long chatId=null;
                    if (update.message() != null) {
                        chatId = update.message().chat().id();
                    }else {
                        chatId = update.editedMessage().chat().id();
                    }
                    bot.execute(new SendMessage(chatId, "Love U, Cat"));
                });
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            });
            String lock = "lock";
            synchronized (lock) {

                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}