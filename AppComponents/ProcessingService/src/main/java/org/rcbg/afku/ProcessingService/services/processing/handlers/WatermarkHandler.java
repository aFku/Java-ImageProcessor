package org.rcbg.afku.ProcessingService.services.processing.handlers;

import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.dto.RabbitMqRequest;
import org.rcbg.afku.ProcessingService.exceptions.IncorrectImageSize;
import org.rcbg.afku.ProcessingService.services.processing.processors.WatermarkProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
public class WatermarkHandler extends BaseImageHandler{


    private WatermarkProcessor processor;

    public WatermarkHandler(WatermarkProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void handle(BufferedImage image, RabbitMqRequest request) throws IOException, IncorrectImageSize {
        if(request.getAttributes().isWatermark()){
            log.debug(image.toString());
            processor.addWatermark(image);
            log.debug(image.toString());
        }
        super.handle(image, request);
    }

}
