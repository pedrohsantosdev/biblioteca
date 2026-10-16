package com.example.biblioteca.resources.exceptions;

import java.time.Instant;

public class StandardError {
    Integer status;
    String error;
    String message;
    Instant instant;
    String path;

    public StandardError() {
    }

    public StandardError(Integer status, String error, String message, Instant instant, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.instant = instant;
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}


