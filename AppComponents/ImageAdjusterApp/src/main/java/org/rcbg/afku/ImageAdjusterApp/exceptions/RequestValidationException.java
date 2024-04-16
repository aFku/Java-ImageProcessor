package org.rcbg.afku.ImageAdjusterApp.exceptions;

public class RequestValidationException extends RuntimeException{
    public RequestValidationException(String message) {
        super(message);
    }
}
