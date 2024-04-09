package org.rcbg.afku.ImageAdjusterApp.services;

import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageMapper;
import org.rcbg.afku.ImageAdjusterApp.dto.RawImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ImageFetchingService {

    @Autowired
    private DatabaseService databaseService;

    public List<ProcessedImageDto> getListOfProcessedImages(String ownerUuid){
        return databaseService.getProcessedImagesList(ownerUuid);
    }

    public ProcessedImageDto getSingleProcessedImage(String ownerUuid, String filename){
        ProcessedImageDto dto = databaseService.getProcessedImage(filename);
        if(!Objects.equals(dto.getOwnerUuid(), ownerUuid)){
            throw new RuntimeException("temporary"); // Create new (access denied)
        }
        return dto;
    }

    public RawImageDto getSingleRawImage(String ownerUuid, String filename){
        RawImageDto dto = databaseService.getRawImageByFilename(filename);
        if(!Objects.equals(dto.getOwnerUuid(), ownerUuid)){
            throw new RuntimeException("temporary"); // Create new (access denied)
        }
        return dto;
    }
}
