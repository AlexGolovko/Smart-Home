package com.gmail.golovkobalak.smarthome.telegram.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.GetChat;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractTelegramBot implements Bot {
    private final TelegramBot bot;

    public AbstractTelegramBot(String token) {
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
