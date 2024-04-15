package org.rcbg.afku.ProcessingService.services.processing;

import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.dto.RabbitMqRequest;
import org.rcbg.afku.ProcessingService.exceptions.IncorrectImageSize;
import org.rcbg.afku.ProcessingService.services.processing.handlers.IImageHandler;
import org.rcbg.afku.ProcessingService.services.processing.handlers.WatermarkHandler;
import org.rcbg.afku.ProcessingService.services.processing.processors.WatermarkProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Service
public class ImageProcessor {

    private IImageHandler chainOfProcess;

    @Autowired
    public ImageProcessor(WatermarkProcessor processor){
        IImageHandler first = new WatermarkHandler(processor);
        this.chainOfProcess = first;
    }

    public BufferedImage process(InputStream in, RabbitMqRequest request) throws IOException, IncorrectImageSize {
        BufferedImage img = ImageIO.read(in);
        this.chainOfProcess.handle(img, request);
        return img;
    }
}
