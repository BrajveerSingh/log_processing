package com.cs.file;

import com.cs.file.exception.FileProcessingException;
import com.cs.file.processor.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private final static String filePath = "C:/Users/brajv/com.test/src/main/resources/logfile.txt";

    public static void main(String[] args) {
        FileProcessor fileProcessor = new FileProcessor();
        try {
            logger.info("Processing log file. filePath={}", filePath);
            fileProcessor.processLog(filePath);
        } catch (FileProcessingException e) {
            logger.error("Unable to process log file.filePath={}", filePath);
        }
    }
}
