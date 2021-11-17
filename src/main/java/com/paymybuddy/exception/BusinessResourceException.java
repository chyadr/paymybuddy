package com.paymybuddy.exception;

import org.springframework.http.HttpStatus;

public class BusinessResourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Long resourceId;
    private HttpStatus status;

    public BusinessResourceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
