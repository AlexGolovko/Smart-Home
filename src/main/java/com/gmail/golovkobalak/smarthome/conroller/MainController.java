package com.gmail.golovkobalak.smarthome.conroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    public static List<String> measures = new ArrayList<>();

    @GetMapping("/home")
    public String getMainMenu() {
        final StringBuilder builder = new StringBuilder();
        measures.forEach(builder::append);
        return "I Am Alive" + builder.toString();
    }

    @GetMapping("/status")
    public String startProgram() {
        return "I am not sure is I alive....";
    }

}
