package com.gmail.golovkobalak.smarthome.cashflow;

import com.gmail.golovkobalak.smarthome.cashflow.strategy.CashMessageStrategy;
import com.gmail.golovkobalak.smarthome.cashflow.strategy.CommandStrategy;
import com.gmail.golovkobalak.smarthome.cashflow.strategy.MessageStrategy;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UpdateCashListener implements UpdatesListener {
    private MessageStrategy strategy;
    private final CashBot cashBot;
    private final CommandStrategy commandStrategy;
    private final CashMessageStrategy cashMessageStrategy;

    public UpdateCashListener(@Lazy CashBot cashBot, CommandStrategy commandStrategy, CashMessageStrategy cashMessageStrategy) {
        this.cashBot = cashBot;
        this.commandStrategy = commandStrategy;
        this.cashMessageStrategy = cashMessageStrategy;
    }

    @Override
    public int process(List<Update> updates) {
        {
            for (final Update update : updates) {
                try {
                    MessageStrategy strategy;
                    Long chatTelegramId = update.message().chat().id();
                    if (update.message().text().startsWith("/")) {
                        strategy = commandStrategy;
                    } else {
                        strategy = cashMessageStrategy;
                    }
                    final String response = strategy.process(update);
                    cashBot.sendMessage(chatTelegramId.toString(), response);
                } catch (Exception e) {
                    log.debug("UpdateCashListener.process", e);
                }
            }
            return CONFIRMED_UPDATES_ALL;
        }
    }
}
