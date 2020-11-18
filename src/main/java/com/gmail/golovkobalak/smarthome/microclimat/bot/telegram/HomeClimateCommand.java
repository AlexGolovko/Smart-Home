package com.gmail.golovkobalak.smarthome.microclimat.bot.telegram;

import com.gmail.golovkobalak.smarthome.microclimat.repo.Measure;
import com.gmail.golovkobalak.smarthome.microclimat.repo.MeasureRepo;
import org.springframework.stereotype.Component;

@Component("/home")
public class HomeClimateCommand implements HomeCommand {
    private final MeasureRepo measureRepo;

    public HomeClimateCommand(MeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    @Override
    public String execute(Long chatId) {
        final Measure measure = measureRepo.findTopByOrderByDateDesc();
        return measure.toYaml();
    }
}
