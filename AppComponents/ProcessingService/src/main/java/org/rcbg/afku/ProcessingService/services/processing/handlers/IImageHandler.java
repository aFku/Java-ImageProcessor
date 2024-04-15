package org.rcbg.afku.ProcessingService.services.processing.handlers;

import org.rcbg.afku.ProcessingService.dto.RabbitMqRequest;
import org.rcbg.afku.ProcessingService.exceptions.IncorrectImageSize;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface IImageHandler {
    public void setNext(IImageHandler handler);
    public void handle(BufferedImage image, RabbitMqRequest request) throws IOException, IncorrectImageSize;
}
