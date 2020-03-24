package com.golovkobalak.smarthome.model;

import com.golovkobalak.smarthome.repo.Measure;
import com.golovkobalak.smarthome.repo.MeasureRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.mongo.MongoHealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.xml.sax.helpers.AttributesImpl;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class SubscribeHandler implements Handler {
    private final ApplicationContext context;
    private final MeasureRepository repository;
    private final FacadeRestService facadeRestService;
    private LocalDateTime time = LocalDateTime.now();

    private Executor executor;

    private static Log logger = LogFactory.getLog(SubscribeHandler.class);

    private Map<String, String> measures = new ConcurrentHashMap();

    {
        executor = Executors.newSingleThreadExecutor();
    }

    @Autowired
    public SubscribeHandler(ApplicationContext context, MeasureRepository repository, FacadeRestService facadeRestService) {
        this.context = context;
        this.repository = repository;
        this.facadeRestService = facadeRestService;
    }

    @Override
    public void handle(String topic, String message) {
        if (measures.containsKey(topic)) {
            Measure measure = createMeasure();
            measure.setMeasures(measures);
            executor.execute(() -> {
                if (LocalDateTime.now().isAfter(time))
                    try {
                        repository.save(measure);
                    } catch (Exception e) {
                        time = LocalDateTime.now().plusHours(24);
                        e.printStackTrace();
                    }
                try {
                    facadeRestService.postMeasure(measure);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            measures = new ConcurrentHashMap<>();
        }
        measures.put(topic, message);
    }

    private Measure createMeasure() {
        return context.getBean(Measure.class);
    }
}
