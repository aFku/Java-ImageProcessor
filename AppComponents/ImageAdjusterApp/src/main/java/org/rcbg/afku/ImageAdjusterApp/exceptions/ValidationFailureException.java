package org.rcbg.afku.ImageAdjusterApp.exceptions;

public class ValidationFailureException extends RuntimeException{

    public ValidationFailureException(String message) {
        super(message);
    }
}
