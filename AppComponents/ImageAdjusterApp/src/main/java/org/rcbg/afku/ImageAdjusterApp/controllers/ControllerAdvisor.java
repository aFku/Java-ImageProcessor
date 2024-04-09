package org.rcbg.afku.ImageAdjusterApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ImageAdjusterApp.exceptions.*;
import org.rcbg.afku.ImageAdjusterApp.responses.ErrorResponse;
import org.rcbg.afku.ImageAdjusterApp.responses.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleImageNotFoundException(ImageNotFoundException ex, HttpServletRequest request){
        log.error("ImageNotFound: " + ex.getMessage());
        return ResponseFactory.createErrorResponse(request.getRequestURI(), HttpStatus.NOT_FOUND, new ArrayList<>(Collections.singleton(ex.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request){
        log.error("AccessDenied: " + ex.getMessage());
        return ResponseFactory.createErrorResponse(request.getRequestURI(), HttpStatus.UNAUTHORIZED, new ArrayList<>(Collections.singleton(ex.getMessage())));
    }

    @ExceptionHandler({FileAlreadyExistException.class, FailedSaveException.class})
    public ResponseEntity<ErrorResponse> handleFileAlreadyExistException(RuntimeException ex, HttpServletRequest request){
        log.error("StorageException: " + ex.getMessage());
        String responseMessage = "Internal server error during saving file";
        return ResponseFactory.createErrorResponse(request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR, new ArrayList<>(Collections.singleton(responseMessage)));
    }

    @ExceptionHandler(JsonException.class)
    public ResponseEntity<ErrorResponse> handleJsonException(JsonException ex, HttpServletRequest request){
        log.error("JsonException: " + ex.getMessage());
        String responseMessage = "Internal server error during processing JSON objects";
        return ResponseFactory.createErrorResponse(request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR, new ArrayList<>(Collections.singleton(responseMessage)));
    }

    @ExceptionHandler(RabbitMqPublishingException.class)
    public ResponseEntity<ErrorResponse> handleRabbitMqPublishingException(RabbitMqPublishingException ex, HttpServletRequest request){
        log.error("RabbitMqException: " + ex.getMessage());
        String responseMessage = "Internal server error during invoking image processing";
        return ResponseFactory.createErrorResponse(request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR, new ArrayList<>(Collections.singleton(responseMessage)));
    }

}
