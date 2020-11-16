package com.gmail.golovkobalak.smarthome.microclimat.repo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//{"date":"2020.11.16 18:32:53", "temperatura":"26", "humidity":"42", "fire":"0", "smoke":"0"}
@Data
@Document
public class Measure {
    @Id
    private String id;
    private String date;
    private int temperatura;
    private int humidity;
    private boolean fire;
    private boolean smoke;

}
