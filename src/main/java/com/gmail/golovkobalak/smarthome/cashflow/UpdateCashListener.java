package com.gmail.golovkobalak.smarthome.cashflow;

import com.gmail.golovkobalak.smarthome.cashflow.strategy.CashMessageStrategy;
import com.gmail.golovkobalak.smarthome.cashflow.strategy.CommandStrategy;
import com.gmail.golovkobalak.smarthome.cashflow.strategy.MessageStrategy;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateCashListener implements UpdatesListener {
    private MessageStrategy strategy;
    private final TelegramBot bot;
    private final CommandStrategy commandStrategy;
    private final CashMessageStrategy cashMessageStrategy;

    public UpdateCashListener(@Lazy TelegramBot bot, CommandStrategy commandStrategy, CashMessageStrategy cashMessageStrategy) {
        this.bot = bot;
        this.commandStrategy = commandStrategy;
        this.cashMessageStrategy = cashMessageStrategy;
    }

    @Override
    public int process(List<Update> updates) {
        {
            for (final Update update : updates) {
                try {
                    final long startTime = System.currentTimeMillis();
                    Long chatTelegramId = update.message().chat().id();
                    if (update.message().text().startsWith("/")) {
                        strategy = commandStrategy;
                    } else {
                        strategy = cashMessageStrategy;
                    }
                    final String response = strategy.process(update);
                    bot.sendMessage(chatTelegramId.toString(), response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return CONFIRMED_UPDATES_ALL;
        }
    }
}
