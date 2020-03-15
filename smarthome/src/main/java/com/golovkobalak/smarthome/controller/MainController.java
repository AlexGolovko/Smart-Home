package com.golovkobalak.smarthome.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/home")
    public String getMainMenu() {
        return "/HOME";
    }

    @PostMapping("/status")
    public String startProgram(){
        return "post";
    }

}