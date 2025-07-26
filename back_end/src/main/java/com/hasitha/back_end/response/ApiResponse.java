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
public class ApiResponse<T> {

    private boolean success;
    private String successCode;
    private String message;
    private T data;
    private String timestamp;

    public ApiResponse() {
        this.success = true;
        this.timestamp = LocalDateTime.now().toString();
    }

    public ApiResponse(String successCode, String message, T data) {
        this.success = true;
        this.successCode = successCode;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now().toString();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
