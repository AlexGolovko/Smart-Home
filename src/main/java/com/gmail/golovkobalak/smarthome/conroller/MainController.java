package com.gmail.golovkobalak.smarthome.conroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/home")
    public String getMainMenu() {
        return "I Am Alive";
    }

    @GetMapping("/status")
    public String startProgram() {
        return "I am not sure is I alive....";
    }

}
