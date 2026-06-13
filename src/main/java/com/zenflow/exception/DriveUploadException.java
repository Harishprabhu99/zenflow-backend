package com.zenflow.exception;

public class DriveUploadException extends RuntimeException {
    public DriveUploadException(String message) {
        super(message);
    }
    public DriveUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
