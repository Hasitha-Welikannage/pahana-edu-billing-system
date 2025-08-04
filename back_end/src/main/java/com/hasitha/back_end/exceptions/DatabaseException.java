package com.hasitha.back_end.exceptions;

/**
 * Custom exception class for database-related errors.
 *
 * Extends {@link AppException} to indicate exceptions occurring specifically
 * during database operations.
 *
 * Used to wrap lower-level SQL or connection exceptions with
 * application-specific context.
 *
 */
public class DatabaseException extends AppException {

    /**
     * Constructs a new DatabaseException with a detailed message and cause.
     *
     * @param m the detailed error message
     * @param c the cause of the exception (e.g., SQLException)
     */
    public DatabaseException(String m, Throwable c) {
        super(m, c);
    }

    /**
     * Constructs a new DatabaseException with a detailed message.
     *
     * @param m the detailed error message
     */
    public DatabaseException(String m) {
        super(m);
    }
}
