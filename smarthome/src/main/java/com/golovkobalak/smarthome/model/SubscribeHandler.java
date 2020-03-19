package com.golovkobalak.smarthome.model;

import com.golovkobalak.smarthome.repo.Measure;
import com.golovkobalak.smarthome.repo.MeasureRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class SubscribeHandler implements Handler {
    private final ApplicationContext context;
    private final MeasureRepository repository;
    private final FacadeRestService facadeRestService;

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
                try {
                    repository.save(measure);
                } catch (Exception e) {
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
