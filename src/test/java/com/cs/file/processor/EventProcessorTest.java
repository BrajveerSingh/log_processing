package com.cs.file.processor;

import com.cs.file.data.EventData;
import com.cs.file.data.State;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventProcessorTest {
    private EventProcessor eventProcessor;
    private EventData eventData;

    @Before
    public void setup(){
        eventProcessor = new EventProcessor();
        eventData = new EventData("testa", State.STARTED, "Test_Log", "1234Test", 0000);
    }

    @Test
    public void processEvent(){
        Assert.assertFalse(eventProcessor.getEventDataByEventId().size() > 0);
        eventProcessor.processEvent(eventData);
        Assert.assertTrue(eventProcessor.getEventDataByEventId().size() > 0);
    }

    @After
    public void tearDown(){
        eventProcessor.getEventDataByEventId().clear();;
        eventData = null;
        eventProcessor = null;
    }
}
