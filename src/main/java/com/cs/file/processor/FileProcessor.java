package com.cs.file.processor;

import com.cs.file.exception.FileProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FileProcessor {
    private Logger logger = LoggerFactory.getLogger(FileProcessor.class);

    public void processLog(final String filePath) throws FileProcessingException {
        logger.info("filePath={}", filePath);
        if (Objects.isNull(filePath) || filePath.isEmpty() || !filePath.endsWith(".txt")) {
            throw new FileProcessingException("Invalid file path. filePath=" + filePath);
        }
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);
        Thread producerThread = new Thread(new Producer(queue, filePath), "LogFileDataReader");
        Thread consumerThread = new Thread(new Consumer(queue), "LogFileDataConsumer");
        logger.info("Starting producer thread.");
        producerThread.start();
        logger.info("Starting consumer thread.");
        consumerThread.start();
    }
}
