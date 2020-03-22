package com.golovkobalak.smarthomebot.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Data
@Component
@Scope("prototype")
public class Measure {
    private static Gson gson;
    private static Type mapType;
    private String id;
    private Map<String, String> measures;
    static {
        gson = new GsonBuilder().setPrettyPrinting().create();
        mapType = new TypeToken<HashMap>() {
        }.getType();

    }
    public String toJson(){
        return gson.toJson(measures, mapType);
    }

}
