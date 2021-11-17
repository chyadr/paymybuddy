package com.paymybuddy.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerControllerException{
    private static final Logger log = LoggerFactory.getLogger(GlobalHandlerControllerException.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BusinessResourceExceptionDTO> unknownError(HttpServletRequest req, Exception ex) {
        log.error("Exception ",ex);
        BusinessResourceExceptionDTO response = new BusinessResourceExceptionDTO();
        response.setErrorMessage(ex.getMessage());
        response.setRequestURL(req.getRequestURL().toString());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessResourceException.class)
    public ResponseEntity<BusinessResourceExceptionDTO> businessResourceError(HttpServletRequest req, BusinessResourceException ex) {
        log.error("BusinessResourceException ",ex);
        BusinessResourceExceptionDTO response = new BusinessResourceExceptionDTO();
        response.setStatus(ex.getStatus());
        response.setErrorMessage(ex.getMessage());
        response.setRequestURL(req.getRequestURL().toString());
        return new ResponseEntity<>(response, ex.getStatus());
    }
}