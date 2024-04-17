package org.rcbg.afku.ProcessingService.services.processing.processors;

import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.exceptions.IncorrectImageSize;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Slf4j
@Service
public class WatermarkProcessor {

    public BufferedImage addWatermark(BufferedImage image) throws IncorrectImageSize, IOException {
        int watermarkSize = calculateWatermarkSize(image);
        BufferedImage watermark = loadAndRescaleWatermark(watermarkSize);
        drawWatermarkOnImage(image, watermark);
        return image;
    }

    // 10% of image size
    private int calculateWatermarkSize(BufferedImage image) throws IncorrectImageSize {
        int height = image.getHeight();
        int width = image.getWidth();

        if(height < 320 || width < 320){
            throw new IncorrectImageSize("Image size is: (" + height + ", " + width + "). Cannot be smaller than (320, 320)");
        }

        height /= 10;
        width /= 10;

        return Math.min(height, width);
    }

    private BufferedImage loadAndRescaleWatermark(int size) throws IOException {
        InputStream in = getClass().getResourceAsStream("/watermark.png");
        BufferedImage originalWatermark = ImageIO.read(in);

        BufferedImage resizedWatermark = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedWatermark.createGraphics();
        graphics2D.drawImage(originalWatermark, 0, 0, size, size, null);
        graphics2D.dispose();
        return resizedWatermark;
    }

    private void drawWatermarkOnImage(BufferedImage image, BufferedImage watermark){
        Graphics graphics = image.getGraphics();
        graphics.drawImage(watermark, watermark.getHeight(), watermark.getWidth(), null);
    }
}
