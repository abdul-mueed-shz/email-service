package com.abdul.email.adapter.in.schedular;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GenericSchedular {
    private final Producer producer;
    private final Consumer consumer;

    public GenericSchedular(Producer producer, Consumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    public void execute() {
        List<Long> ids;
        do {
            ids = producer.produce();
            if (!ids.isEmpty()) {
                log.info("{} records pulled for processing", ids.size());
            }
            ids.forEach(consumer::consume);
        } while (!ids.isEmpty());
    }
}
