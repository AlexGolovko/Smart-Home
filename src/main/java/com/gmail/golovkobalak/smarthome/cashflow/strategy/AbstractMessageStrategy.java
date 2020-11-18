package com.gmail.golovkobalak.smarthome.cashflow.strategy;

import com.gmail.golovkobalak.smarthome.cashflow.repo.CashFlowRepo;
import com.gmail.golovkobalak.smarthome.cashflow.repo.CashStateRepo;
import com.gmail.golovkobalak.smarthome.cashflow.repo.ChatRepo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class AbstractMessageStrategy implements MessageStrategy {
    protected static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public final ChatRepo chatRepo;
    public final CashStateRepo cashStateRepo;
    public final CashFlowRepo cashFlowRepo;

    protected AbstractMessageStrategy(ChatRepo chatRepo, CashStateRepo cashStateRepo, CashFlowRepo cashFlowRepo) {
        this.chatRepo = chatRepo;
        this.cashStateRepo = cashStateRepo;
        this.cashFlowRepo = cashFlowRepo;
    }
}
