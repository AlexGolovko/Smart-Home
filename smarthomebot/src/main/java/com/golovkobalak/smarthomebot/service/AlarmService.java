package com.golovkobalak.smarthomebot.service;

import com.golovkobalak.smarthomebot.data.Measure;


public interface AlarmService {
    public void handle(Measure measure);
}
