package com.cs.file.processor;

import com.cs.file.exception.FileProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private Logger logger = LoggerFactory.getLogger(Producer.class);
    private final String filePath;
    private BlockingQueue<String> queue;
    private boolean stopProcessing;

    Producer(final BlockingQueue<String> queue, final String filePath) throws FileProcessingException {
        logger.info("filePath={}", filePath);
        if (Objects.isNull(filePath)
                || filePath.isEmpty()
                || !filePath.endsWith(".txt")
                || Objects.isNull(queue)) {
            throw new FileProcessingException("Invalid file path. filePath=" + filePath);
        }
        this.queue = queue;
        this.filePath = filePath;
    }

    void stop() {
        stopProcessing = true;
    }

    boolean isStopProcessing(){
        return stopProcessing;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))))) {
            logger.info("Producer has started processing for file={}", filePath);
            while (!isStopProcessing()) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    queue.put(line);
                }
            }
            logger.info("Producer has stopped processing for file={}, stopProcessing={}", filePath, stopProcessing);
        } catch (FileNotFoundException e) {
            logger.error("Unable to find log file at the given file path. filePath={}", filePath);
            throw new RuntimeException("Unable to find log file at the given file path.");
        } catch (IOException e) {
            logger.error("Unable to read log file at the given file path. filePath={}", filePath);
            throw new RuntimeException("Unable to read log file at the given file path.");
        } catch (InterruptedException e) {
            logger.error("Processing of the file has got interrupted. filePath={}", filePath);
            throw new RuntimeException("Processing of the file has got interrupted.");
        }
    }
}