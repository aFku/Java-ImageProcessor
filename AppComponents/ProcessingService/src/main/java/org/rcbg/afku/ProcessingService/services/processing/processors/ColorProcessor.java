package org.rcbg.afku.ProcessingService.services.processing.processors;

import org.rcbg.afku.ProcessingService.dto.ImageColorConversion;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ColorProcessor {

    public BufferedImage convertColor(BufferedImage image, ImageColorConversion colorConversion) {
        switch (colorConversion){
            case GRAYSCALE -> {
                setGrayscale(image);
            }
            case NEGATIVE -> {
                setNegative(image);
            }
        }
        return image;
    }


    private void setNegative(BufferedImage image){
        int height = image.getHeight();
        int width = image.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                p = (a << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(x, y, p);
            }
        }
    }

    private void setGrayscale(BufferedImage image){
        int height = image.getHeight();
        int width = image.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int oldrgb = image.getRGB(x,y);
                Color color = new Color(oldrgb);
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                int rgb = (oldrgb & 0xff000000) | (gray << 16) | (gray << 8) | gray;
                image.setRGB(x, y, rgb);
            }
        }
    }

}
