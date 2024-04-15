package org.rcbg.afku.ProcessingService.services.processing.handlers;

import org.rcbg.afku.ProcessingService.dto.RabbitMqRequest;
import org.rcbg.afku.ProcessingService.exceptions.IncorrectImageSize;

import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class BaseImageHandler implements IImageHandler{

    IImageHandler nextHandler = null;

    @Override
    public void setNext(IImageHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handle(BufferedImage image, RabbitMqRequest request) throws IOException, IncorrectImageSize {
        if(this.nextHandler != null){
            this.nextHandler.handle(image, request);
        }
    }
}
