package com.gmail.golovkobalak.smarthome.microclimat.bot.telegram;

import com.gmail.golovkobalak.smarthome.telegram.bot.AbstractTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PropertySource("classpath:bot.properties")
public class MicroClimateBot extends AbstractTelegramBot {

    public MicroClimateBot(@Value("${climatebot.token}") String token) {
        super(token);
    }
}
