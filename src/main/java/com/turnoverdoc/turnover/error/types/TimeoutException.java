package com.turnoverdoc.turnover.error.types;

/**
 * Timeout exception type. Uses only for RestTemplate object
 */
public class TimeoutException extends RuntimeException {
    public TimeoutException(String message) {
        super(message);
    }
}
