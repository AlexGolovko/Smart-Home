package com.gmail.golovkobalak.smarthome.cashflow.command;

import com.gmail.golovkobalak.smarthome.cashflow.repo.CashFlow;
import com.gmail.golovkobalak.smarthome.cashflow.repo.CashFlowRepo;
import com.gmail.golovkobalak.smarthome.cashflow.repo.Chat;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component("/lastmonth")
public class LastMonthCommand implements CashBotCommand {
    private CashFlowRepo cashFlowRepo;

    public LastMonthCommand(CashFlowRepo cashFlowRepo) {
        this.cashFlowRepo = cashFlowRepo;
    }

    @Override
    public String execute(Chat chat, String command) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -31);
        final Date date = cal.getTime();
        final List<CashFlow> cashFlows = cashFlowRepo.findAllByChatAndCreateDateGreaterThan(chat, date);
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
}
