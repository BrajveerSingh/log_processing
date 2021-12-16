package com.cs.file.processor;

import com.cs.file.data.Event;
import com.cs.file.data.EventData;
import com.cs.file.data.persister.DbPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class EventProcessor {
    private Logger logger = LoggerFactory.getLogger(EventProcessor.class);

    private Map<String, EventData> eventDataByEventId;
    private AtomicReference<DbPersister> dbPersister;

    public EventProcessor() {
        eventDataByEventId = new ConcurrentHashMap<>();
        dbPersister = new AtomicReference<>(new DbPersister());
    }

    public void processEvent(final EventData eventData) {
        EventData event = eventDataByEventId.get(eventData.getId());
        if (Objects.nonNull(event)) {
            eventDataByEventId.remove(eventData.getId());
            long duration = Math.abs(event.getTimestamp() - eventData.getTimestamp());
            boolean alert = false;
            if (duration > 4) {
                alert = true;
            }
            logger.debug("duration={}, alert={}", duration, alert);
            storeInDb(event, alert, duration, dbPersister);
        } else {
            eventDataByEventId.put(eventData.getId(), eventData);
        }
    }

    private void storeInDb(final EventData eventData,
                           final boolean alert,
                           final long duration,
                           AtomicReference<DbPersister> dbPersister) {
        new Thread(() -> {
            Event event =
                    new Event(
                            eventData.getId(),
                            duration,
                            alert,
                            eventData.getType(),
                            eventData.getHost()
                    );
            DbPersister persister = dbPersister.get();
            logger.debug("Persisting event={} in db.", event);
            if(Objects.nonNull(persister)) {
                persister.persistEvent(event);
            }
        }).start();
    }

    Map<String, EventData> getEventDataByEventId() {
        return eventDataByEventId;
    }
}

