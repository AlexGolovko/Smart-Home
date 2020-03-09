package com.golovkobalak.smarthome;

import com.golovkobalak.smarthome.model.CloudSubscriber;
import com.golovkobalak.smarthome.model.SubscribeHandler;
import com.golovkobalak.smarthome.model.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IContext;

@Component
public class Runner {

    private Subscriber subscriber;
    private SubscribeHandler subscribeHandler;

    @Autowired
    public Runner(Subscriber cloudSubscriber, SubscribeHandler subscribeHandler) {
        this.subscriber = cloudSubscriber;
        this.subscribeHandler = subscribeHandler;
    }

    public void run() {
        Subscriber subscribeTemp = subscriber.subscribe("sensors/+");
        while (true) {

        }

    }
}
