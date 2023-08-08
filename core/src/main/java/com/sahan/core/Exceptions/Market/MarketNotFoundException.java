package com.sahan.core.Exceptions.Market;

/**
 * Custom exception class representing an exception that occurs when a market is not found.
 * This exception extends the standard RuntimeException class, making it an unchecked exception.
 * Unchecked exceptions do not require explicit catch blocks, and they propagate up the call stack until they are handled.
 */
public class MarketNotFoundException extends RuntimeException {

    /**
     * Constructs a new MarketNotFoundException with the given error message.
     *
     * @param message The error message describing the reason for the exception.
     */
    public MarketNotFoundException(String message) {
        // Call the constructor of the parent class (RuntimeException) with the provided message.
        super(message);
    }
}
