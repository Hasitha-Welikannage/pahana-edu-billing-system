package com.hasitha.back_end.exceptions;

/**
 * Custom unchecked exception for application-specific errors.
 *
 * Extends {@link RuntimeException} to represent runtime exceptions that can be
 * thrown during application execution.
 *
 * Provides constructors to specify error message and optional cause.
 */
public class AppException extends RuntimeException {

    /**
     * Constructs a new AppException with the specified detail message and
     * cause.
     *
     * @param message the detailed error message
     * @param cause the cause of the exception (can be null)
     */
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AppException with the specified detail message.
     *
     * @param message the detailed error message
     */
    public AppException(String message) {
        super(message);
    }
}
