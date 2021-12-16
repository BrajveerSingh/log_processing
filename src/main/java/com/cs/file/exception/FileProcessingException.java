package com.cs.file.exception;

public class FileProcessingException extends Exception {
    public FileProcessingException(final String message){
        super(message);
    }

    public FileProcessingException(final String message, final Throwable th){
        super(message, th);
    }
}
