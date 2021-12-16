package com.cs.file.processor;

import com.cs.file.exception.FileProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerTest {
    private Producer producer;
    private String filePath;
    private BlockingQueue<String> queue;
    @Before
    public void setup() throws FileProcessingException {
        filePath = "C:/Users/brajv/com.test/src/test/resources/logfile.txt";
        queue = new ArrayBlockingQueue<>(10);
        producer = new Producer(queue,filePath);
    }

    @Test
    public void isStopProcessing() {
        Assert.assertFalse(producer.isStopProcessing());
        Thread thread = new Thread(producer);
        thread.start();
        Assert.assertFalse(producer.isStopProcessing());
        producer.stop();
        Assert.assertTrue(producer.isStopProcessing());
    }

    @Test(expected = FileProcessingException.class)
    public void procuderConstructorWhenWrongFileExtension() throws FileProcessingException {
        producer = new Producer(queue, "a.doc");
    }

    @Test(expected = FileProcessingException.class)
    public void procuderConstructorWhenQueueIsNull() throws FileProcessingException {
        producer = new Producer(null, "a.doc");
    }

    @Test
    public void run(){
        Thread thread = new Thread(producer);
        thread.start();
        Assert.assertFalse(producer.isStopProcessing());
        producer.stop();
        Assert.assertTrue(producer.isStopProcessing());
    }
}
