package com.zenflow.exception;

public class InvalidChecksumException extends RuntimeException {
    public InvalidChecksumException(String message) {
        super(message);
    }
    public InvalidChecksumException(String message, Throwable cause) {
        super(message, cause);
    }
}
