package com.gmail.golovkobalak.smarthome.cashflow.strategy;

import com.gmail.golovkobalak.smarthome.cashflow.command.CashBotCommand;
import com.gmail.golovkobalak.smarthome.cashflow.repo.CashFlowRepo;
import com.gmail.golovkobalak.smarthome.cashflow.repo.CashStateRepo;
import com.gmail.golovkobalak.smarthome.cashflow.repo.Chat;
import com.gmail.golovkobalak.smarthome.cashflow.repo.ChatRepo;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

@Slf4j
@Component
public class CommandStrategy extends AbstractMessageStrategy {
    private static final String UNSUPPORTED_COMMAND = "Unsupported yet\n" + "Available commands:\n" +
            "/clear - clean current spending state.\n" +
            "/lastmonth - show all spending for last 31 days.";
    private static final String EMPTY_STRING = "";
    private final Map<String, CashBotCommand> commands;

    public CommandStrategy(ChatRepo chatRepo, CashStateRepo cashStateRepo, CashFlowRepo cashFlowRepo, Map<String, CashBotCommand> commands) {
        super(chatRepo, cashStateRepo, cashFlowRepo);
        this.commands = commands;
    }


    @Override
    public String process(Update update) {
        final Future<String> future = executorService.submit(() -> {
            final Long chatId = update.message().chat().id();
            final Optional<Chat> optionalChat = chatRepo.findFirstByChatId(chatId);
            if (optionalChat.isEmpty()) {
                return "chat is not known";
            }
            var text = update.message().text().trim().toLowerCase();
            var command = text.substring(text.indexOf("/"), text.contains("@") ? text.indexOf("@") : text.length());
            final CashBotCommand cashBotCommand = commands.get(command);
            if (cashBotCommand == null) {
                return UNSUPPORTED_COMMAND;
            }
            return cashBotCommand.execute(optionalChat.get(), command);
        });
        try {
            return future.get();
        } catch (Exception e) {
            log.debug("CommandStrategy.class", e);
            return EMPTY_STRING;
        }
    }
}
