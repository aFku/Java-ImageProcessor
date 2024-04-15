package org.rcbg.afku.ProcessingService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.dto.*;
import org.rcbg.afku.ProcessingService.exceptions.FailedSaveException;
import org.rcbg.afku.ProcessingService.exceptions.FileAlreadyExistException;
import org.rcbg.afku.ProcessingService.exceptions.IncorrectImageSize;
import org.rcbg.afku.ProcessingService.services.processing.ImageProcessor;
import org.rcbg.afku.ProcessingService.services.rabbitmq.RabbitMqRequester;
import org.rcbg.afku.ProcessingService.services.storage.ImageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Service
public class Callbacks {

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private RabbitMqRequester requester;

    @Autowired
    ImageProcessor imageProcessor;

    private final RabbitMqResponseBuilder responseBuilder;

    public Callbacks(){
        this.responseBuilder = new RabbitMqResponseBuilder();
    }

    public void receiveProcessingRequest(String requestMessage){
        RabbitMqRequest requestObject = new RabbitMqRequest();
        RabbitMqResponse responseObject = null;
        String processedFilename = null;

        try{
            requestObject = RabbitMqMessageMapper.createRequestObject(requestMessage);
            InputStream in = imageManager.getRawImageStream(requestObject.getRawFilename());
            BufferedImage out = imageProcessor.process(in, requestObject);
            processedFilename = imageManager.saveBufferedImage(out);
        } catch (JsonProcessingException ex) {
            responseBuilder.withStatus(Status.FAILURE);
            responseBuilder.withMessage(ex.getMessage());
            responseObject = errorBuildAndLog(ex);
        } catch (IOException | IncorrectImageSize ex){
            responseBuilder.withStatus(Status.FAILURE);
            responseBuilder.withMessage(ex.getMessage());
            responseBuilder.withRawFilename(requestObject.getRawFilename());
            responseBuilder.withAttributes(requestObject.getAttributes());
            responseObject = errorBuildAndLog(ex);
        } catch (FileAlreadyExistException | FailedSaveException ex) {
            responseBuilder.withStatus(Status.FAILURE);
            responseBuilder.withMessage(ex.getMessage());
            responseBuilder.withRawFilename(requestObject.getRawFilename());
            responseBuilder.withAttributes(requestObject.getAttributes());
            responseBuilder.withProcessedFilename(processedFilename);
            responseObject = errorBuildAndLog(ex);
        }

        if(responseBuilder.getStatus() != Status.FAILURE){
            responseObject = prepareSuccessResponse(processedFilename, requestObject);
        }

        try {
            requester.sendMessage(RabbitMqMessageMapper.createStringMessageFromResponseObject(responseObject));
        } catch (Exception ex){
            log.error("Cannot send response due to error: " + ex.getMessage());
        }
    }

    private RabbitMqResponse errorBuildAndLog(Exception ex){
        RabbitMqResponse responseObject = responseBuilder.build();
        responseBuilder.reset();
        log.error(ex.getClass().getName() + ": " + ex.getMessage());
        return responseObject;
    }

    private RabbitMqResponse prepareSuccessResponse(String processedFilename, RabbitMqRequest request){
        responseBuilder.withStatus(Status.SUCCESS);
        responseBuilder.withMessage("File: " + request.getRawFilename() + " has been successful processed and saved as: " + processedFilename);
        responseBuilder.withAttributes(request.getAttributes());
        responseBuilder.withProcessedFilename(processedFilename);
        responseBuilder.withRawFilename(request.getRawFilename());
        RabbitMqResponse response = responseBuilder.build();
        responseBuilder.reset();
        return response;
    }
}
