package org.rcbg.afku.ProcessingService.services.processing.handlers;

import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.dto.RabbitMqRequest;
import org.rcbg.afku.ProcessingService.exceptions.IncorrectImageSize;
import org.rcbg.afku.ProcessingService.services.processing.processors.CropProcessor;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
public class CropHandler extends BaseImageHandler {

    private CropProcessor processor;

    public CropHandler(CropProcessor processor) {
        this.processor = processor;
    }

    @Override
    public BufferedImage handle(BufferedImage image, RabbitMqRequest request) throws IOException, IncorrectImageSize {
        int w = request.getAttributes().getCropWidth();
        int h = request.getAttributes().getCropHeight();
        BufferedImage croppedImage;
        if(w > 0 && h > 0) {
            log.debug("Cropping image: " + request.getRawFilename() + " to - X: 0, Y: 0, W: " + request.getAttributes().getCropWidth() + ", H: " + request.getAttributes().getCropHeight());
            croppedImage = new BufferedImage(w, h, image.getType());
            croppedImage.createGraphics().drawImage(processor.cropImage(image, w, h), 0, 0, null);
            log.debug("Image: " + request.getRawFilename() + " has been successful cropped!");
        } else {
            croppedImage = image;
        }
        return super.handle(croppedImage, request);
    }

}
