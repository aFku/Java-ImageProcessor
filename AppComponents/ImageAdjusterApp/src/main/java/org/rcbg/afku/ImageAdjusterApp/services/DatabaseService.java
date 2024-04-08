package org.rcbg.afku.ImageAdjusterApp.services;

import org.rcbg.afku.ImageAdjusterApp.domain.ProcessedImage;
import org.rcbg.afku.ImageAdjusterApp.domain.ProcessedImageRepository;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImage;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImageRepository;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageProcessAttributes;
import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    private RawImageRepository rawImageRepository;

    @Autowired
    private ProcessedImageRepository processedImageRepository;

    public RawImage saveRawImageRecord(String filename, String ownerUuid){
        RawImage image = new RawImage();
        image.setFilename(filename);
        image.setOwnerUuid(ownerUuid);
        return rawImageRepository.save(image);
    }

    public ProcessedImage saveProcessedImage(String filename, String rawImageFilename, ImageProcessAttributes attributes){
        RawImage rawImage = rawImageRepository.getRawImageByFilename(rawImageFilename).orElseThrow(() -> new RuntimeException("temporary")); // Create exception
        ProcessedImage processedImage = ProcessedImageMapper.INSTANCE.toEntity(filename, rawImage, attributes);
        return processedImageRepository.save(processedImage);
    }
}
