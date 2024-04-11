package org.rcbg.afku.ImageAdjusterApp.services;

import org.rcbg.afku.ImageAdjusterApp.dto.IFile;
import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.RawImageDto;
import org.rcbg.afku.ImageAdjusterApp.exceptions.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ImageFetchingService {

    @Autowired
    private DatabaseService databaseService;

    public List<ProcessedImageDto> getListOfProcessedImages(String userUuid){
        return databaseService.getProcessedImagesList(userUuid);
    }

    public ProcessedImageDto getSingleProcessedImage(String userUuid, String filename){
        ProcessedImageDto dto = databaseService.getProcessedImage(filename);
        checkOwnership(dto, userUuid);
        return dto;
    }

    public RawImageDto getSingleRawImage(String userUuid, String filename){
        RawImageDto dto = databaseService.getRawImageByFilename(filename);
        checkOwnership(dto, userUuid);
        return dto;
    }

    private void checkOwnership(IFile dto, String ownerUuid){
        if(!Objects.equals(dto.getOwnerUuid(), ownerUuid)){
            throw new AccessDeniedException("UserID: " + ownerUuid + " has no ownership on image: " + dto.getFilename());
        }
    }
}
