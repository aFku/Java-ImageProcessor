package org.rcbg.afku.ProcessingService.services.processing.handlers;

import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.dto.ImageColorConversion;
import org.rcbg.afku.ProcessingService.dto.RabbitMqRequest;
import org.rcbg.afku.ProcessingService.exceptions.IncorrectImageSize;
import org.rcbg.afku.ProcessingService.services.processing.processors.ColorProcessor;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@Service
public class ColorConversionHandler extends BaseImageHandler {

    private ColorProcessor processor;

    public ColorConversionHandler(ColorProcessor processor) {
        this.processor = processor;
    }

    @Override
    public BufferedImage handle(BufferedImage image, RabbitMqRequest request) throws IOException, IncorrectImageSize {
        if(request.getAttributes().getColorConversion() != ImageColorConversion.NONE) {
            log.debug("Converting image: " + request.getRawFilename() + " to " + request.getAttributes().getColorConversion());
            image = processor.convertColor(image, request.getAttributes().getColorConversion());
            log.debug("Image: " + request.getRawFilename() + " has been successful converted!");
        }
        return super.handle(image, request);
    }
}
