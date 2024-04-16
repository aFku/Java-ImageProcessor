package org.rcbg.afku.ImageAdjusterApp.services;

import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImageRepository;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageProcessAttributes;
import org.rcbg.afku.ImageAdjusterApp.exceptions.RequestValidationException;
import org.rcbg.afku.ImageAdjusterApp.exceptions.ValidationFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class RequestValidator {

    @Value("${storage.rawImages.destination}")
    private String rawImagesDestination;

    public void validate(String filename, ImageProcessAttributes attributes){
        this.CheckCropSize(filename, attributes);
    }

    private void CheckCropSize(String filename, ImageProcessAttributes attributes){
        File file = new File(rawImagesDestination, filename);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if(height < attributes.getCropHeight() || width < attributes.getCropWidth()){
                String message = "You cannot specify crop area (%d, %d) larger than image size (%d, %d)";
                message = String.format(message, attributes.getCropWidth(), attributes.getCropHeight(), width, height);
                log.error("Error during validating image: " + filename);
                throw new RequestValidationException(message);
            }
        } catch (IOException ex){
            log.error("Image: " + filename + " could not be validated");
            throw new ValidationFailureException(ex.getMessage());
        }
        log.debug("Image: " + filename + " - crop area is correct");
    }
}
