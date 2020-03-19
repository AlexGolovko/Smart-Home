package com.golovkobalak.smarthomebot.bot;

public interface Bot<T> {
    public void  sendAlarmMessage(T t);
}
