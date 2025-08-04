package com.hasitha.back_end.exceptions;

/**
 * Custom exception class for validation errors.
 *
 * Extends {@link AppException} to represent exceptions caused by invalid input
 * or violated validation rules.
 */
public class ValidationException extends AppException {

    /**
     * Constructs a new ValidationException with the specified detail message.
     *
     * @param m the detail message describing the validation error
     */
    public ValidationException(String m) {
        super(m);
    }
}
