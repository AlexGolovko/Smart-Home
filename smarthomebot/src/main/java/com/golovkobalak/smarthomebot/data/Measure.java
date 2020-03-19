package com.golovkobalak.smarthomebot.data;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Data
@Component
@Scope("prototype")
public class Measure {
    private String id;
    private Map<String, String> measures;

}
