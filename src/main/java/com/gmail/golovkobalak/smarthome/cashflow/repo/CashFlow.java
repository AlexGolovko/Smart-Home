package com.gmail.golovkobalak.smarthome.cashflow.repo;

import com.pengrad.telegrambot.model.Message;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class CashFlow {
    @Id
    private String id;
    private Chat chat;
    private String spenderName;
    private String spenderId;
    private int moneySum;
    private Date createDate;
    private String comment;

    public static CashFlow parse(final Message message) {
        final CashFlow cashFlow = new CashFlow();
        cashFlow.spenderName = message.from().firstName();
        cashFlow.spenderId = String.valueOf(message.from().id());
        final String messageText = message.text().trim();
        if (messageText.contains(" ")) {
            final int spaceIndex = messageText.indexOf(' ');
            final String moneySum = messageText.substring(0, spaceIndex);
            cashFlow.moneySum = Integer.parseInt(moneySum);
            cashFlow.comment = messageText.substring(spaceIndex + 1);
        } else {
            cashFlow.moneySum = Integer.parseInt(messageText);
        }
        cashFlow.createDate = new Date();
        return cashFlow;
    }
}
