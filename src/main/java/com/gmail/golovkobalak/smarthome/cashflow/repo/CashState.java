package com.gmail.golovkobalak.smarthome.cashflow.repo;


import com.pengrad.telegrambot.model.Message;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
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
}
