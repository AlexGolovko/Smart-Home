package com.golovkobalak.smarthomebot.data;

import com.golovkobalak.smarthomebot.bot.TelegramBotImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MeasureTest {
    private static final Logger logger = LoggerFactory.getLogger(MeasureTest.class);
    @Test
    void toJson() {
        Measure measure = prepareLowLevelMeasure();
        logger.info(measure.toJson());
    }

    private Measure prepareLowLevelMeasure() {
        Measure measure = new Measure();
        var measures = new HashMap<String, String>();
        measures.put("sensors/temperature", "23");
        measures.put("sensors/humidity", "56");
        measures.put("sensors/fire", "0");
        measures.put("sensors/smoke", "1000");
        measures.put("sensors/date", "dayTime");
        measure.setMeasures(measures);
        measure.setId("dadsfasdfasdfasdf");
        return measure;
    }
}