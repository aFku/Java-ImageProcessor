package org.rcbg.afku.ProcessingService.services.processing.processors;

import org.rcbg.afku.ProcessingService.dto.RabbitMqRequest;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class CropProcessor {

    public BufferedImage cropImage(BufferedImage image, int w, int h) {
        return image.getSubimage(0, 0, w, h);
    }
}
