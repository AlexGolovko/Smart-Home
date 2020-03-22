package com.golovkobalak.smarthomebot.bot;

import com.pengrad.telegrambot.UpdatesListener;

public interface Bot<T> {
    void  sendAlarmMessage(T t);
    void setUpdateHandler(UpdatesListener listener);

}
