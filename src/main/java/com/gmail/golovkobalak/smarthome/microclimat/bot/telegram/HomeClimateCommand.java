package com.gmail.golovkobalak.smarthome.microclimat.bot.telegram;

import com.gmail.golovkobalak.smarthome.microclimat.repo.Measure;
import com.gmail.golovkobalak.smarthome.microclimat.repo.MeasureRepo;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component("/home")
public class HomeClimateCommand implements HomeCommand {
    private static final Gson GSON = new Gson();
    private final MeasureRepo measureRepo;

    public HomeClimateCommand(MeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    @Override
    public String execute(Long chatId) {
        final Measure measure = measureRepo.findTopByOrderByDateDesc();
        return GSON.toJson(measure);
    }
}
