package com.gmail.golovkobalak.smarthome.microclimat.repo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//{"date":"2020.11.16 18:32:53", "temperatura":"26", "humidity":"42", "fire":"0", "smoke":"0"}
@Data
@Document
public class Measure {
    @Id
    private String id;
    private Date date;
    private int temperatura;
    private int humidity;
    private boolean fire;
    private boolean smoke;

    public String toYaml() {
        return "measure: " +
                "\n  temperature: " + temperatura +
                "\n  humidity: " + humidity +
                "\n  fire: " + fire +
                "\n  smoke: " + smoke;
    }
}
