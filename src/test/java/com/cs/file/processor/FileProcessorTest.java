package com.cs.file.processor;

import com.cs.file.exception.FileProcessingException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class FileProcessorTest {
    private FileProcessor fileProcessor;
    private String filePath;

    @Before
    public void setup(){
        fileProcessor = new FileProcessor();
        filePath = "C:/Users/brajv/com.test/src/test/resources/logfile.txt";
    }

    @Test(expected = FileProcessingException.class)
    public void processLogWhenFileExtensionNotTxt() throws FileProcessingException {
        filePath = "C:/Users/brajv/com.test/src/test/resources/test.txtt";
        fileProcessor.processLog(filePath);
    }
    @Test
    public void processLog() throws FileProcessingException {
        fileProcessor.processLog(filePath);
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        System.out.println(allStackTraces);
        Set<Thread> threads = allStackTraces.keySet();
        boolean isLogFileDataConsumer = false;
        boolean isLogFileDataReader = false;
        for(Thread thread : threads){
            if(thread.getName().equalsIgnoreCase("LogFileDataReader")){
                isLogFileDataReader = true;
            }
            if(thread.getName().equalsIgnoreCase("LogFileDataConsumer")){
                isLogFileDataConsumer = true;
            }
        }
        Assert.assertTrue(isLogFileDataConsumer);
        Assert.assertTrue(isLogFileDataReader);
    }



    @After
    public void tearDown(){
        fileProcessor = null;
    }

}
