package com.golovkobalak.smarthome.repo;


import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@Scope("prototype")
public class Measure {

    @Id
    private Long id;
    private Map<String, String> measures;
}

