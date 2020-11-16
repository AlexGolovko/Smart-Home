package com.gmail.golovkobalak.smarthome.cashflow.repo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Chat {
    @Id
    private String id;
    private Long chatId;
    private String name;
}
