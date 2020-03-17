package com.golovkobalak.smarthomebot.bot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;

//@Component
@PropertySource("classpath:bot.properties")
public class HomeBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(HomeBot.class);

    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String botName;


    /* @PostConstruct
     private void init() throws TelegramApiRequestException {
         TelegramBotsApi botsApi = new TelegramBotsApi();
         botsApi.registerBot(this);
     }*/
    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", botName, token);
    }

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId()).setText("Hello World");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {

    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
