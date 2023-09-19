package org.example.Lab2_BLPS.common.throwable.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
