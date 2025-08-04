package com.hasitha.back_end.response;

import java.time.LocalDateTime;

/**
 * Represents a standardized error response for API calls.
 *
 * This class encapsulates error details such as: - success flag (always false)
 * - error message - error code - detailed error information (optional) -
 * timestamp of the error occurrence
 *
 * Used to communicate errors consistently from the backend to clients.
 */
public class ErrorResponse {

    // Indicates that this response represents a failure (always false)
    private boolean success;

    // Human-readable message describing the error
    private String message;

    // Code identifying the type/category of error
    private String errorCode;

    // Optional detailed error information (e.g., validation errors)
    private Object errors;

    // Timestamp when the error response was created (ISO-8601 format)
    private String timestamp;

    /**
     * Default constructor. Does not set fields explicitly.
     */
    public ErrorResponse() {
    }

    /**
     * Constructs an ErrorResponse with error code, message, and details. Sets
     * success to false and timestamp to the current time.
     *
     * @param errorCode code identifying the error
     * @param message descriptive message of the error
     * @param errors detailed error information (optional)
     */
    public ErrorResponse(String errorCode, String message, Object errors) {
        this.success = false;
        this.message = message;
        this.errorCode = errorCode;
        this.errors = errors;
        this.timestamp = LocalDateTime.now().toString();
    }

    /**
     * Returns the success flag (always false).
     *
     * @return false indicating an error response
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success flag.
     *
     * @param success the success status (usually false)
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the error message.
     *
     * @return the error message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message the error message string
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the error code.
     *
     * @return the error code string
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code.
     *
     * @param errorCode the error code string
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Returns detailed error information.
     *
     * @return an object containing detailed errors
     */
    public Object getErrors() {
        return errors;
    }

    /**
     * Sets detailed error information.
     *
     * @param errors an object containing detailed errors
     */
    public void setErrors(Object errors) {
        this.errors = errors;
    }

    /**
     * Returns the timestamp of when the error response was created.
     *
     * @return ISO-8601 formatted timestamp string
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the error response.
     *
     * @param timestamp ISO-8601 formatted timestamp string
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
