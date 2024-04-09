package org.rcbg.afku.ImageAdjusterApp.services;

import org.rcbg.afku.ImageAdjusterApp.domain.ProcessedImage;
import org.rcbg.afku.ImageAdjusterApp.domain.ProcessedImageRepository;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImage;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImageRepository;
import org.rcbg.afku.ImageAdjusterApp.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseService {

    @Autowired
    private RawImageRepository rawImageRepository;

    @Autowired
    private ProcessedImageRepository processedImageRepository;

    public RawImageDto saveRawImageRecord(String filename, String ownerUuid){
        RawImage image = new RawImage();
        image.setFilename(filename);
        image.setOwnerUuid(ownerUuid);
        return RawImageMapper.INSTANCE.toDto(rawImageRepository.save(image));
    }

    public ProcessedImageDto saveProcessedImage(String filename, String rawImageFilename, ImageProcessAttributes attributes){
        RawImage rawImage = rawImageRepository.getRawImageByFilename(rawImageFilename).orElseThrow(() -> new RuntimeException("temporary")); // Create exception
        ProcessedImage processedImage = ProcessedImageMapper.INSTANCE.toEntity(filename, rawImage, attributes);
        return ProcessedImageMapper.INSTANCE.toDto(processedImageRepository.save(processedImage));
    }

    public RawImageDto getRawImageByFilename(String filename){
        RawImage image = rawImageRepository.getRawImageByFilename(filename).orElseThrow(() -> new RuntimeException("temporary")); // Create Exception
        return RawImageMapper.INSTANCE.toDto(rawImageRepository.save(image));
    }

    public List<ProcessedImageDto> getProcessedImagesList(String ownerUuid){
        return processedImageRepository.getProcessedImagesByRawImage_OwnerUuid(ownerUuid).stream().map(ProcessedImageMapper.INSTANCE::toDto).toList();
    }

    public ProcessedImageDto getProcessedImage(String filename){
        ProcessedImage image = processedImageRepository.getProcessedImageByFilename(filename).orElseThrow(() -> new RuntimeException("temporary")); // Create Exception
        return ProcessedImageMapper.INSTANCE.toDto(image);
    }
}
