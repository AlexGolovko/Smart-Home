package com.gmail.golovkobalak.smarthome.cashflow;

import com.pengrad.telegrambot.UpdatesListener;

public interface Bot {
    void sendMessage(String chatId, String message);

    void setUpdateListener(UpdatesListener listener);
}