package org.rcbg.afku.ProcessingService.exceptions;

public class FileAlreadyExistException extends Exception{
    public FileAlreadyExistException(String message) {
        super(message);
    }
}