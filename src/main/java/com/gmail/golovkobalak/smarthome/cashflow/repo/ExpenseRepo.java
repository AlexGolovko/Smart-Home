package com.gmail.golovkobalak.smarthome.cashflow.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepo extends MongoRepository<Expense, String> {
}
