package com.gmail.golovkobalak.smarthome.cashflow.command;

import com.gmail.golovkobalak.smarthome.cashflow.repo.CashState;
import com.gmail.golovkobalak.smarthome.cashflow.repo.CashStateRepo;
import com.gmail.golovkobalak.smarthome.cashflow.repo.Chat;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("/clear")
public class ClearCommand implements CashBotCommand {
    private static final String ALL_CLEAR = "all clear";
    private CashStateRepo cashStateRepo;

    public ClearCommand(CashStateRepo cashStateRepo) {
        this.cashStateRepo = cashStateRepo;
    }

    @Override
    public String execute(Chat chat, String command) {
        final List<CashState> cashStates = cashStateRepo.findAllByChat(chat);
        cashStates.forEach(cashState -> {
            cashState.setCashState(0L);
            cashStateRepo.save(cashState);
        });
        return ALL_CLEAR;
    }
}
