package com.gmail.golovkobalak.smarthome.cashflow.strategy;

import com.gmail.golovkobalak.smarthome.cashflow.repo.*;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Future;

@Component
public class CommandStrategy extends AbstractMessageStrategy {
    private static final List<String> CLEAR_COMMANDS = Arrays.asList("/clear", "/clear@LouieHolovkoBot");
    private static final String ALL_CLEAR = "all clear";
    private static final String UNSUPPORTED_COMMAND = "Unsupported yet\n" + "Available commands:\n" +
            "/clear - clean current spending state.\n" +
            "/lastmonth - show all spending for last 31 days.";
    private static final List<String> LAST_MONTH_COMMANDS = Arrays.asList("/lastmonth", "/lastmonth@LouieHolovkoBot");
    private static final String EMPTY_STRING = "";

    public CommandStrategy(ChatRepo chatRepo, CashStateRepo cashStateRepo, CashFlowRepo cashFlowRepo) {
        super(chatRepo, cashStateRepo, cashFlowRepo);
    }


    @Override
    public String process(Update update) {
        final Future<String> future = executorService.submit(() -> {
            final Long chatId = update.message().chat().id();
            final Optional<Chat> optionalChat = chatRepo.findFirstByChatId(chatId);
            if (optionalChat.isEmpty()) {
                return "chat is not known";
            }
            if (CLEAR_COMMANDS.contains(update.message().text().trim())) {
                final List<CashState> cashStates = cashStateRepo.findAllByChat(optionalChat.get());
                cashStates.forEach(cashState -> {
                    cashState.setCashState(0L);
                    cashStateRepo.save(cashState);
                });
                return ALL_CLEAR;
            }
            if (LAST_MONTH_COMMANDS.contains(update.message().text().trim())) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -31);
                final Date date = cal.getTime();
                final List<CashFlow> cashFlows = cashFlowRepo.findAllByChatAndCreateDateGreaterThan(optionalChat.get(), date);
                final StringBuilder builder = new StringBuilder();
                int sum = 0;
                for (CashFlow cashFlow : cashFlows) {
                    sum += cashFlow.getMoneySum();
                    builder.append(cashFlow.getCreateDate())
                            .append('\t')
                            .append(cashFlow.getSpenderName())
                            .append(':')
                            .append(cashFlow.getMoneySum())
                            .append('\t')
                            .append(cashFlow.getComment() == null ? "" : cashFlow.getComment())
                            .append('\n');
                }
                builder.append("Result: ").append(sum);
                return builder.toString();
            }

            return UNSUPPORTED_COMMAND;
        });
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return EMPTY_STRING;
        }
    }
}
