package org.rcbg.afku.ProcessingService.services.storage;

import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.exceptions.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

    public InputStream getRawImageStream(String filename){
        return loadInputStream(rawImagesDestination, filename);
    }

}
