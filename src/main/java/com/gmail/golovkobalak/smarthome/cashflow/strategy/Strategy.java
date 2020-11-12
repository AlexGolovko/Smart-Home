package com.gmail.golovkobalak.smarthome.cashflow.strategy;

import com.pengrad.telegrambot.model.Update;

public interface Strategy {
    String process(Update update);
}
