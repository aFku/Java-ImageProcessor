package org.rcbg.afku.ImageAdjusterApp.exceptions;

public class FileAlreadyExistException extends RuntimeException{
    public FileAlreadyExistException(String message) {
        super(message);
    }
}
