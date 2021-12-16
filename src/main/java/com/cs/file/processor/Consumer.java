package com.cs.file.processor;

import com.cs.file.data.EventData;
import com.cs.file.data.State;
import com.cs.file.data.StringToEventConverter;
import com.cs.file.data.persister.DbPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private Logger logger = LoggerFactory.getLogger(Consumer.class);
    private BlockingQueue<String> queue;
    private boolean stopProcessing;

    Consumer(final BlockingQueue<String> queue) {
        if(Objects.isNull(queue)) {
            throw new IllegalArgumentException("The argument queue is null.");
        }
        this.queue = queue;
    }

    void stop() {
        stopProcessing = true;
    }

    boolean isStopProcessing(){
        return stopProcessing;
    }

    @Override
    public void run() {
        StringToEventConverter converter = new StringToEventConverter();
        EventProcessor eventProcessor = new EventProcessor();
        while (!stopProcessing) {
            try {
                String line = queue.take();
                EventData eventData = converter.getEventData(line);
                if(Objects.nonNull(eventData)){
                    logger.debug("Sending event data to event processor. eventData={}", eventData);
                    eventProcessor.processEvent(eventData);
                }
            } catch (InterruptedException e) {
                logger.error("Consumer Thread got interrupted.", e);
                throw new RuntimeException("Consumer thread has got interrupted.");
            }
        }
    }
}