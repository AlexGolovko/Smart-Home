package com.gmail.golovkobalak.smarthome.cashflow.strategy;

import com.pengrad.telegrambot.model.Update;

public interface MessageStrategy {
    String process(Update update);
}
