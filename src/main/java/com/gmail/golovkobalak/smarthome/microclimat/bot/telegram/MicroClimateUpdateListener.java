package com.gmail.golovkobalak.smarthome.microclimat.bot.telegram;

import com.gmail.golovkobalak.smarthome.telegram.bot.Bot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MicroClimateUpdateListener implements UpdatesListener {
    private static final String UNSUPPORTED_COMMAND = "Unsupported yet\n" + "Available commands:\n" +
            "/home - shows last measure";
    private Map<String, HomeCommand> commandMap;
    private Bot microClimateBot;

    public MicroClimateUpdateListener(Map<String, HomeCommand> commandMap, Bot microClimateBot) {
        this.commandMap = commandMap;
        this.microClimateBot = microClimateBot;
    }

    @Override
    public int process(List<Update> updates) {
        {
            for (final Update update : updates) {
                try {
                    Long chatTelegramId = update.message().chat().id();
                    if (update.message().text().startsWith("/")) {
                        var text = update.message().text().trim().toLowerCase();
                        var command = text.substring(text.indexOf("/"), text.contains("@") ? text.indexOf("@") : text.length());
                        final HomeCommand homeCommand = commandMap.get(command);
                        if (homeCommand != null) {
                            final String result = homeCommand.execute(chatTelegramId);
                            microClimateBot.sendMessage(String.valueOf(chatTelegramId), result);
                        } else {
                            microClimateBot.sendMessage(String.valueOf(chatTelegramId), UNSUPPORTED_COMMAND);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return CONFIRMED_UPDATES_ALL;
        }
    }
}
