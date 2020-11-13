package com.gmail.golovkobalak.smarthome.cashflow.command;

import com.gmail.golovkobalak.smarthome.cashflow.repo.Chat;

public interface CashBotCommand {
    String execute(Chat chat, String command);

}
