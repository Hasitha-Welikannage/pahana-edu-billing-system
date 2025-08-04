package com.hasitha.back_end.exceptions;

/**
 * Custom exception class indicating that a requested resource was not found.
 *
 * Extends {@link AppException} to signal a "not found" error condition,
 * typically used when an entity does not exist in the database or data source.
 */
public class NotFoundException extends AppException {

    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param m the detail message explaining the exception
     */
    public NotFoundException(String m) {
        super(m);
    }
}
