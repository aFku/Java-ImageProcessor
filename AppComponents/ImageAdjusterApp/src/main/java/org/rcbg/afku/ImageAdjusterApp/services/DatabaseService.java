package org.rcbg.afku.ImageAdjusterApp.services;

import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ImageAdjusterApp.domain.ProcessedImage;
import org.rcbg.afku.ImageAdjusterApp.domain.ProcessedImageRepository;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImage;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImageRepository;
import org.rcbg.afku.ImageAdjusterApp.dto.*;
import org.rcbg.afku.ImageAdjusterApp.exceptions.ImageNotFoundException;
import org.rcbg.afku.ImageAdjusterApp.exceptions.ImagesLinkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DatabaseService {

    @Autowired
    private RawImageRepository rawImageRepository;

    @Autowired
    private ProcessedImageRepository processedImageRepository;

    public RawImageDto saveRawImageRecord(String filename, String ownerUuid){
        RawImage image = new RawImage();
        image.setRawFilename(filename);
        image.setOwnerUuid(ownerUuid);
        log.info("Saving raw image to database: {\"filename\": \"" + filename + "\"}");
        return RawImageMapper.INSTANCE.toDto(rawImageRepository.save(image));
    }

    public ProcessedImageDto saveProcessedImage(String processedFilename, String rawFilename, ImageProcessAttributes attributes) throws ImagesLinkException {
        RawImage rawImage = rawImageRepository.getRawImageByRawFilename(rawFilename)
                .orElseThrow(() -> new ImagesLinkException("Cannot find raw image " + rawFilename + " to connect with processed " + processedFilename));
        log.info("Saving processed image to database: {\"processedFilename\": \"" + processedFilename + "\", \"attributes\": " + attributes.toString() + "}");
        ProcessedImage processedImage = ProcessedImageMapper.INSTANCE.toEntity(processedFilename, rawImage, attributes);
        return ProcessedImageMapper.INSTANCE.toDto(processedImageRepository.save(processedImage));
    }

    public RawImageDto getRawImageByFilename(String filename){
        RawImage image = rawImageRepository.getRawImageByRawFilename(filename).orElseThrow(() -> new ImageNotFoundException("Cannot find raw image " + filename));
        return RawImageMapper.INSTANCE.toDto(rawImageRepository.save(image));
    }

    public List<ProcessedImageDto> getProcessedImagesList(String ownerUuid){
        return processedImageRepository.getProcessedImagesByRawImage_OwnerUuid(ownerUuid).stream().map(ProcessedImageMapper.INSTANCE::toDto).toList();
    }

    public ProcessedImageDto getProcessedImage(String filename){
        ProcessedImage image = processedImageRepository.getProcessedImageByProcessedFilename(filename).orElseThrow(() -> new ImageNotFoundException("Cannot find processed image " + filename));
        return ProcessedImageMapper.INSTANCE.toDto(image);
    }
}
