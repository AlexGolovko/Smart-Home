package com.gmail.golovkobalak.smarthome.microclimat.validator;

import com.gmail.golovkobalak.smarthome.microclimat.repo.Measure;
import org.springframework.stereotype.Component;

@Component
public class AlarmMeasureValidator implements AlarmValidator<Measure> {
    public boolean validate(Measure measure) {
        return !(measure.isFire() || measure.isSmoke() || measure.getHumidity() > 80 || measure.getTemperatura() > 50);
    }
}
