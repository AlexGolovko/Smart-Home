package com.gmail.golovkobalak.smarthome.cashflow.repo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document
public class Expense {
    @Id
    private String id;
    private String spenderName;
    private int sumPay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpenderName() {
        return spenderName;
    }

    public void setSpenderName(String spenderName) {
        this.spenderName = spenderName;
    }

    public int getSumPay() {
        return sumPay;
    }

    public void setSumPay(int sumPay) {
        this.sumPay = sumPay;
    }
}
