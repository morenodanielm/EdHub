package com.edhub.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.edhub.exceptions.EdhubExceptions;


@ControllerAdvice
public class ExceptionsController extends ResponseEntityExceptionHandler{

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {EdhubExceptions.class})
    protected ResponseEntity<Object> handleConflict(
            EdhubExceptions ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getHttpStatus(), request);
    }
    
    
}
