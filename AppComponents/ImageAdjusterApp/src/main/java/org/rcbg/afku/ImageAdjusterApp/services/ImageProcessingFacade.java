package org.rcbg.afku.ImageAdjusterApp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ImageAdjusterApp.dto.*;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.*;
import org.rcbg.afku.ImageAdjusterApp.exceptions.ImagesLinkException;
import org.rcbg.afku.ImageAdjusterApp.exceptions.JsonException;
import org.rcbg.afku.ImageAdjusterApp.exceptions.RabbitMqPublishingException;
import org.rcbg.afku.ImageAdjusterApp.services.rabbitmq.RabbitMqRequester;
import org.rcbg.afku.ImageAdjusterApp.services.rabbitmq.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@Validated
public class ImageProcessingFacade {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private RabbitMqService rabbitMqRequester;

    @Autowired
    private StorageService storageService;

    @Autowired
    private RequestValidator requestValidator;

    @Validated
    public RabbitMqRequest receiveRawImage(MultipartFile multipartFile, @Valid ImageProcessAttributes attributes, String ownerId) {
        String savedRawFilename = storageService.saveRawFile(multipartFile);
        requestValidator.validate(savedRawFilename, attributes);
        databaseService.saveRawImageRecord(savedRawFilename, ownerId);
        RabbitMqRequest rabbitRequest = RabbitMqMessageMapper.createRequestObject(savedRawFilename, attributes);
        try {
            String requestMessage = RabbitMqMessageMapper.requestObjectToJsonString(rabbitRequest);
            rabbitMqRequester.sendMessage(requestMessage);
        } catch (JsonProcessingException ex) {
            throw new JsonException("Cannot process object to JSON: " + ex.getMessage());
        } catch (IOException ex){
            throw new RabbitMqPublishingException("Cannot publish message to RabbitMQ queue: " + ex.getMessage());
        }
        return rabbitRequest;
    }
}
