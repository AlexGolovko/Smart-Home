package com.gmail.golovkobalak.smarthome.cashflow;


import com.gmail.golovkobalak.smarthome.telegram.bot.Bot;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class Louis {
    @Lazy
    private Bot cashBot;
    private UpdateCashListener listener;

    public Louis(Bot cashBot, UpdateCashListener listener) {
        this.cashBot = cashBot;
        this.listener = listener;
    }

    public void run() {
        cashBot.setUpdateListener(listener);
    }

}
