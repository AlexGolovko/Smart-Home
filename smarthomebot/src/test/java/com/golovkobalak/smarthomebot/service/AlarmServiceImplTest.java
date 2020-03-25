package com.golovkobalak.smarthomebot.service;

import com.golovkobalak.smarthomebot.bot.Bot;
import com.golovkobalak.smarthomebot.data.Measure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.mockito.Mockito.*;


class AlarmServiceImplTest {

    @Mock
    private Bot bot;
    @InjectMocks
    @Spy
    private AlarmServiceImpl service;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void thenMeasureLowAlarmLevel() {
        Measure measure = prepareLowLevelMeasure();
        service.handle(measure);
        verifyNoInteractions(bot);
    }

    @Test
    public void thenMeasureHigherAlarmLevel() {
        Measure measure = prepareAlarmLevelMeasure();
        service.handle(measure);
        verify(bot).sendAlarmMessage(measure);
    }

    private Measure prepareAlarmLevelMeasure() {
        var measure = prepareLowLevelMeasure();
        measure.getMeasures().put("sensors/temperature", "85");
        return measure;
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