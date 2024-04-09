package org.rcbg.afku.ImageAdjusterApp.exceptions;

public class RabbitMqPublishingException extends RuntimeException{
    public RabbitMqPublishingException(String message) {
        super(message);
    }
}
