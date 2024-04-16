package org.rcbg.afku.ProcessingService.services.processing.handlers;

import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.dto.RabbitMqRequest;
import org.rcbg.afku.ProcessingService.exceptions.IncorrectImageSize;
import org.rcbg.afku.ProcessingService.services.processing.processors.WatermarkProcessor;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
public class WatermarkHandler extends BaseImageHandler{


    private WatermarkProcessor processor;

    public WatermarkHandler(WatermarkProcessor processor) {
        this.processor = processor;
    }

    @Override
    public BufferedImage handle(BufferedImage image, RabbitMqRequest request) throws IOException, IncorrectImageSize {
        if(request.getAttributes().isWatermark()){
            log.debug("Adding watermark to: " + request.getRawFilename());
            image = processor.addWatermark(image);
            log.debug("Watermark added successful!");
        }
        return super.handle(image, request);
    }

}
