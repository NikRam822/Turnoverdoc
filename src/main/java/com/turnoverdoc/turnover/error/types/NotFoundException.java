package com.turnoverdoc.turnover.error.types;

/**
 * Not found exception type. Uses only for RestTemplate object
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
