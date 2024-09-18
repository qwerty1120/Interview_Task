package com.example.yourssu.dto;

public class ExceptionResponse {
    private String time;
    private String status;
    private String message;
    private String requestURI;

    // Constructors
    public ExceptionResponse(String time, String status, String message, String requestURI) {
        this.time = time;
        this.status = status;
        this.message = message;
        this.requestURI = requestURI;
    }

    // Getters and Setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }
}

