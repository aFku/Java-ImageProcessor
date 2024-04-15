package org.rcbg.afku.ProcessingService.services.storage;

import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.exceptions.FailedSaveException;
import org.rcbg.afku.ProcessingService.exceptions.FileAlreadyExistException;
import org.rcbg.afku.ProcessingService.exceptions.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

@Slf4j
@Service
public class ImageManager {

    @Value("${storage.rawImages.destination}")
    private String rawImagesDestination;

    @Value("${storage.processedImages.destination}")
    private String processedImagesDestination;

    private InputStream loadInputStream(String path, String filename){
        InputStream in;
        File file = new File(path, filename);
        try{
            in = new FileInputStream(file.getAbsoluteFile());
        }catch (FileNotFoundException ex){
            log.error("FileNotFoundException: " + ex.getMessage());
            throw new ImageNotFoundException("Cannot find file under path: " + file.getAbsoluteFile());
        }
        return in;
    }

    private String generateFileName(){
        String extension = "jpeg";
        UUID uuidName = UUID.randomUUID();
        return uuidName.toString() + "." + extension;
    }

    public InputStream getRawImageStream(String filename){
        return loadInputStream(rawImagesDestination, filename);
    }

    public String saveBufferedImage(BufferedImage bufferedImage) throws FileAlreadyExistException, FailedSaveException {
        String filename = generateFileName();
        File file = new File(processedImagesDestination, filename);
        if(file.exists()) {
            log.error(String.format("File: %s already exists", file.getAbsoluteFile()));
            throw new FileAlreadyExistException(String.format("Cannot save file because: %s already exists in storage", file.getAbsoluteFile()));
        }
        try{
            ImageIO.write(bufferedImage, "jpeg", file);
            log.info("File saved to the storage: " + file.getAbsoluteFile());
        } catch (IOException ex){
            log.error("Cannot save file: " + file.getAbsoluteFile() + " | " + ex.getMessage());
            throw new FailedSaveException("Error during saving file: " + file.getAbsoluteFile());
        }
        return filename;
    }
}
