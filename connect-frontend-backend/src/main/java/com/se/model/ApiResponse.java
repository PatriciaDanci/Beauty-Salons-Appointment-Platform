package com.se.model;

public class ApiResponse {
    private String message;
    private boolean status;

    private Object data;

    // Default Constructor
    public ApiResponse() {}

    // Parameterized Constructor
    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public ApiResponse(String message, boolean status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    // Getter for 'message'
    public String getMessage() {
        return message;
    }

    // Setter for 'message'
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter for 'status'
    public boolean isStatus() {
        return status;
    }

    // Setter for 'status'
    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
