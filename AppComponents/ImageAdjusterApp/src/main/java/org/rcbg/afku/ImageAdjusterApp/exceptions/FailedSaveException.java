package org.rcbg.afku.ImageAdjusterApp.exceptions;

public class FailedSaveException extends RuntimeException{
    public FailedSaveException(String message) {
        super(message);
    }
}
