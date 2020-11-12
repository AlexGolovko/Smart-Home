package com.gmail.golovkobalak.smarthome.cashflow.repo;


import com.pengrad.telegrambot.model.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope("prototype")
@Document
public class CashState {
    @Id
    private String id;
    private Chat chat;
    private Long spenderId;
    private String spenderName;
    private Long cashState;

    public static CashState parse(Message message) {
        final CashState cashState = new CashState();
        cashState.spenderId = (long) message.from().id();
        cashState.spenderName = message.from().firstName();
        final String messageText = message.text().trim();
        if (messageText.contains(" ")) {
            final int spaceIndex = messageText.indexOf(' ');
            final String moneySum = messageText.substring(0, spaceIndex);
            cashState.cashState = Long.parseLong(moneySum);
        } else {
            cashState.cashState = Long.parseLong(messageText);
        }
        return cashState;
    }

    public String toMessage() {
        return this.spenderName + " : " + this.cashState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Long getSpenderId() {
        return spenderId;
    }

    public void setSpenderId(Long spenderId) {
        this.spenderId = spenderId;
    }

    public String getSpenderName() {
        return spenderName;
    }

    public void setSpenderName(String spenderName) {
        this.spenderName = spenderName;
    }

    public Long getCashState() {
        return cashState;
    }

    public void setCashState(Long cashState) {
        this.cashState = cashState;
    }
}
