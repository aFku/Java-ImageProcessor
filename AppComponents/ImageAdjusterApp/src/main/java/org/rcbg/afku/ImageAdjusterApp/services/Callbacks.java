package org.rcbg.afku.ImageAdjusterApp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqMessageMapper;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqResponse;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.Status;
import org.rcbg.afku.ImageAdjusterApp.exceptions.ImagesLinkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Callbacks {

    @Autowired
    private DatabaseService databaseService;

    public void receiveProcessedImage(String responseMessage){
        RabbitMqResponse responseObject;
        ProcessedImageDto processedImageDto;
        try {
            responseObject = RabbitMqMessageMapper.JsonStringToResponse(responseMessage);
            processedImageDto = databaseService.saveProcessedImage(responseObject.getProcessedFilename(), responseObject.getRawFilename(), responseObject.getAttributes());
        } catch (JsonProcessingException ex){
            return ; // Send error over websocket
        } catch (ImagesLinkException ex) {
            return; // Send error over websocket
        }
        if(responseObject.getProcessStatus().getStatus() == Status.FAILURE){
            return; // send error over web socket
        } else {
            return; // send data over websocket
        }
    }
}
