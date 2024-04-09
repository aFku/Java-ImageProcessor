package org.rcbg.afku.ImageAdjusterApp.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.rcbg.afku.ImageAdjusterApp.domain.ProcessedImage;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImage;
import org.rcbg.afku.ImageAdjusterApp.dto.*;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageProcessingFacade {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private RabbitMqService rabbitMqService;

    @Autowired
    private StorageService storageService;

    public RabbitMqRequest receiveRawImage(MultipartFile multipartFile, ImageProcessAttributes attributes, String ownerId) {
        String savedFilename = storageService.saveFile(multipartFile);
        databaseService.saveRawImageRecord(savedFilename, ownerId);
        RabbitMqRequest rabbitRequest = RabbitMqMessageMapper.createRequestObject(savedFilename, attributes);
        try {
            String requestMessage = RabbitMqMessageMapper.requestObjectToJsonString(rabbitRequest);
            rabbitMqService.SendMessage(requestMessage);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return rabbitRequest;
    }

    public void receiveProcessedImage(String responseMessage){
        RabbitMqResponse responseObject;
        try {
            responseObject = RabbitMqMessageMapper.JsonStringToResponse(responseMessage);
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
            return;
        }
        if(responseObject.getProcessStatus().getStatus() == Status.FAILURE){
            throw new RuntimeException(responseObject.getProcessStatus().getMessage()); // Create new
        } else {
            ProcessedImageDto processedImageDto = databaseService.saveProcessedImage(responseObject.getProcessedFilename(), responseObject.getRawFilename(), responseObject.getAttributes());
            // Send Data over websocket
        }
    }
}
