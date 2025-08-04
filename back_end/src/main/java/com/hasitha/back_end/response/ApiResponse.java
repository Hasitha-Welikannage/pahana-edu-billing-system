package com.hasitha.back_end.response;

import java.time.LocalDateTime;

/**
 * Generic class representing a standardized API response.
 *
 * @param <T> the type of the response data payload
 *
 * This class encapsulates common response fields such as: - success status -
 * success code - message - response data - timestamp of the response creation
 *
 * It is used to wrap all API responses consistently.
 *
 */
public class ApiResponse<T> {

    // Indicates if the API call was successful
    private boolean success;

    // A code representing the success type or category
    private String successCode;

    // Human-readable message about the API call result
    private String message;

    // Data payload returned by the API (generic type)
    private T data;

    // Timestamp when the response was created (ISO string)
    private String timestamp;

    /**
     * Default constructor. Sets success to true and initializes timestamp to
     * current time.
     */
    public ApiResponse() {
        this.success = true;
        this.timestamp = LocalDateTime.now().toString();
    }

    /**
     * Constructor to create an ApiResponse with specific fields. Sets success
     * to true and timestamp to current time.
     *
     * @param successCode the code representing success
     * @param message a message describing the response
     * @param data the data payload of the response
     */
    public ApiResponse(String successCode, String message, T data) {
        this.success = true;
        this.successCode = successCode;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now().toString();
    }

    /**
     * Returns whether the API call was successful.
     *
     * @return true if successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the response.
     *
     * @param success true if successful, false otherwise
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the success code of the response.
     *
     * @return the success code string
     */
    public String getSuccessCode() {
        return successCode;
    }

    /**
     * Sets the success code of the response.
     *
     * @param successCode the success code string
     */
    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    /**
     * Returns the message describing the response.
     *
     * @return the message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message describing the response.
     *
     * @param message the message string
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the data payload of the response.
     *
     * @return the data of generic type T
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data payload of the response.
     *
     * @param data the data of generic type T
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Returns the timestamp of when the response was created.
     *
     * @return ISO-8601 string timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the response.
     *
     * @param timestamp ISO-8601 string timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
