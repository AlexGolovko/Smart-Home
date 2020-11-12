package com.gmail.golovkobalak.smarthome.cashflow;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class Louis {
    @Lazy
    private Bot louisBot;
    private UpdateCashListener listener;

    public Louis(Bot louisBot, UpdateCashListener listener) {
        this.louisBot = louisBot;
        this.listener = listener;
    }

    public void run() {
        louisBot.setUpdateListener(listener);
    }

}
