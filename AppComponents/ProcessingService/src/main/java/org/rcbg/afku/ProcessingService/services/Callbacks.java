package org.rcbg.afku.ProcessingService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.dto.*;
import org.rcbg.afku.ProcessingService.services.rabbitmq.RabbitMqRequester;
import org.rcbg.afku.ProcessingService.services.storage.ImageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
public class Callbacks {

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private RabbitMqRequester requester;

    private final RabbitMqResponseBuilder responseBuilder;

    public Callbacks(){
        this.responseBuilder = new RabbitMqResponseBuilder();
    }

    public void receiveProcessingRequest(String requestMessage){
        RabbitMqRequest requestObject;
        RabbitMqResponse responseObject = null;
        try{
            requestObject = RabbitMqMessageMapper.createRequestObject(requestMessage);
            InputStream in = imageManager.getRawImageStream(requestObject.getRawFilename());
            // Processing stuff
            // Saving to storage stuff
        } catch (JsonProcessingException ex) {
            responseBuilder.withStatus(Status.FAILURE);
            responseBuilder.withMessage(ex.getMessage());
            responseObject = responseBuilder.build();
            responseBuilder.reset();
            log.error(ex.getClass().getName() + ": " + ex.getMessage());
        }

        try {
            requester.sendMessage(RabbitMqMessageMapper.createStringMessageFromResponseObject(responseObject));
        } catch (Exception ex){
            log.error("Cannot send response due to error: " + ex.getMessage());
        }
    }
}
