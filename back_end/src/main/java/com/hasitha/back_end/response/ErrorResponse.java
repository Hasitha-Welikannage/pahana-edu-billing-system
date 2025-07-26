/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.response;

import java.time.LocalDateTime;

/**
 *
 * @author hasithawelikannage
 */
public class ErrorResponse {

    private String message;
    private String errorCode;
    private Object errors;
    private String path;
    private String timestamp;

    public ErrorResponse() {
    }
    
    public ErrorResponse(String message, String errorCode, Object errors, String path) {
        this.message = message;
        this.errorCode = errorCode;
        this.errors = errors;
        this.path = path;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
