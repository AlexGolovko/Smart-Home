package com.gmail.golovkobalak.smarthome.cashflow.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CashStateRepo extends MongoRepository<CashState, String> {

    List<CashState> findAllByChat(Chat chat);
}
