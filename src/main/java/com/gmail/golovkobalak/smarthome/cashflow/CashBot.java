package com.gmail.golovkobalak.smarthome.cashflow;

import com.gmail.golovkobalak.smarthome.telegram.bot.AbstractTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Slf4j
@Lazy
@Component
@PropertySource("classpath:bot.properties")
public class CashBot extends AbstractTelegramBot {

    private com.pengrad.telegrambot.TelegramBot bot;

    public CashBot(@Value("${cashbot.token}") String token) {
        super(token);
    }
}
