package com.gmail.golovkobalak.smarthome.cashflow.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface CashFlowRepo extends MongoRepository<CashFlow, String> {

    List<CashFlow> findAllByChatAndCreateDateGreaterThan(Chat chat, Date date);
}
