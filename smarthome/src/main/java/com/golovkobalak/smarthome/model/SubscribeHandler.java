package com.golovkobalak.smarthome.model;

import com.golovkobalak.smarthome.repo.Measure;
import com.golovkobalak.smarthome.repo.MeasureRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Component
public class SubscribeHandler implements Handler {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private MeasureRepository repository;
    @Autowired
    private FacadeRestService facadeRestService;

    private Executor executor;

    private static Log logger = LogFactory.getLog(SubscribeHandler.class);

    private Map<String, String> measures = new ConcurrentHashMap();

    {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void handle(String topic, String message) {
        if (measures.containsKey(topic)) {
            Measure measure = createMeasure();
            measure.setMeasures(measures);
            executor.execute(() -> {
                try {
                    repository.save(measure);
                    facadeRestService.postMeasure(measure);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            measures=new ConcurrentHashMap<>();
        }
        measures.put(topic, message);
    }

    private Measure createMeasure() {
        return context.getBean(Measure.class);
    }
}
