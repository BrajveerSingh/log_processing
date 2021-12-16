package com.cs.file.processor;

import com.cs.file.exception.FileProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConsumerTest {

    private Consumer consumer;
    private BlockingQueue<String> queue;
    @Before
    public void setup() {
        queue = new ArrayBlockingQueue<>(10);
        consumer = new Consumer(queue);
    }

    @Test
    public void isStopProcessing() {
        Assert.assertFalse(consumer.isStopProcessing());
        Thread thread = new Thread(consumer);
        thread.start();
        Assert.assertFalse(consumer.isStopProcessing());
        consumer.stop();
        Assert.assertTrue(consumer.isStopProcessing());
    }

    @Test(expected = IllegalArgumentException.class)
    public void consumerConstructorWithNullQueue(){
        consumer = new Consumer(null);
    }

    @Test
    public void consumerConstructor(){
        consumer = new Consumer(queue);
        Assert.assertFalse(consumer.isStopProcessing());
    }

    @Test
    public void run(){
        Thread thread = new Thread(consumer);
        thread.start();
        Assert.assertFalse(consumer.isStopProcessing());
        consumer.stop();
        Assert.assertTrue(consumer.isStopProcessing());
    }
}
