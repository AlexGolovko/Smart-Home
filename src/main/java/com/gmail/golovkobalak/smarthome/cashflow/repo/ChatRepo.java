package com.gmail.golovkobalak.smarthome.cashflow.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ChatRepo extends MongoRepository<Chat, String> {

    Optional<Chat> findFirstByChatId(Long chatId);
}
