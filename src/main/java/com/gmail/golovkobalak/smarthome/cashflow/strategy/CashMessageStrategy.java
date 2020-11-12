package com.gmail.golovkobalak.smarthome.cashflow.strategy;

import com.gmail.golovkobalak.smarthome.cashflow.repo.*;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

@Component
public class CashMessageStrategy extends AbstractMessageStrategy {


    public CashMessageStrategy(ChatRepo chatRepo, CashStateRepo cashStateRepo, CashFlowRepo cashFlowRepo) {
        super(chatRepo, cashStateRepo, cashFlowRepo);
    }

    @Override
    public String process(Update update) {
        final Future<String> future = executorService.submit(() -> {
            String response = "";
            final Optional<Chat> optionalChat = chatRepo.findFirstByChatId(update.message().chat().id());
            Chat chat;
            if (optionalChat.isEmpty()) {
                chat = handleNewChat(update.message());
                response = "Hello " + update.message().from().firstName() + " \n" +
                        "I will help U with money saving (no)\n" +
                        "Available commands:\n" +
                        "/clear - clean current spending state.\n" +
                        "/lastmonth - show all spending for last 31 days.\n" +
                        "Your first record:\n";
            } else {
                chat = optionalChat.get();
            }
            handleExistingChat(chat, update.message());

            return response + toResponse(chat);
        });
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return "Are U sure that is the correct cash amount?";
        }

    }

    private void handleExistingChat(Chat chat, Message message) {
        final List<CashState> cashStateList = cashStateRepo.findAllByChat(chat);
        final CashFlow cashFlow = CashFlow.parse(message);
        cashFlow.setChat(chat);
        final int moneySum = cashFlow.getMoneySum();
        cashFlowRepo.save(cashFlow);
        CashState cashStateNew = null;
        for (CashState cashState : cashStateList) {
            if (message.from().id().longValue() == cashState.getSpenderId()) {
                final long cashStateValue = cashState.getCashState() + moneySum;
                cashStateNew = cashState;
                cashStateNew.setCashState(cashStateValue);
            }
        }
        if (cashStateNew == null) {
            cashStateNew = CashState.parse(message);
            cashStateNew.setChat(chat);
        }
        cashStateRepo.save(cashStateNew);
    }

    private Chat handleNewChat(Message message) {
        com.pengrad.telegrambot.model.Chat chatTelegram = message.chat();
        final Chat chat = new Chat();
        chat.setChatId(chatTelegram.id());
        if (chatTelegram.title() == null) {
            chat.setName(chatTelegram.firstName() + ":" + chatTelegram.lastName());
        }
        chat.setName(chatTelegram.title());
        return chatRepo.save(chat);
    }

    private String toResponse(Chat chat) {
        final List<CashState> cashStates = cashStateRepo.findAllByChat(chat);
        StringBuilder builder = new StringBuilder();
        for (CashState cashState : cashStates) {
            builder.append(cashState.toMessage()).append('\n');
        }
        if (cashStates.size() == 2) {
            final CashState cashStateOne = cashStates.get(0);
            final CashState cashStateTwo = cashStates.get(1);
            if (cashStateOne.getCashState() > cashStateTwo.getCashState()) {
                builder.append("Delta: ").append(cashStateOne.getSpenderName()).append(':').append(cashStateOne.getCashState() - cashStateTwo.getCashState());
            } else {
                builder.append("Delta: ").append(cashStateTwo.getSpenderName()).append(':').append(cashStateTwo.getCashState() - cashStateOne.getCashState());
            }
        }
        return builder.toString();
    }
}


