package com.golovkobalak.smarthome;

import com.golovkobalak.smarthome.model.SubscribeHandler;
import com.golovkobalak.smarthome.model.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Runner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    private Subscriber subscriber;
    private SubscribeHandler subscribeHandler;

    @Autowired
    public Runner(Subscriber cloudSubscriber, SubscribeHandler subscribeHandler) {
        this.subscriber = cloudSubscriber;
        this.subscribeHandler = subscribeHandler;
    }

    public void run()  {
        logger.info(Thread.currentThread().getName());
        Subscriber subscribeTemp = subscriber.subscribe("sensors/+");
        synchronized (subscribeTemp) {
            try {
                subscribeTemp.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
