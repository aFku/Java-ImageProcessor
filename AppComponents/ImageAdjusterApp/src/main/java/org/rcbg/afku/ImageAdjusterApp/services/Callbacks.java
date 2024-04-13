package org.rcbg.afku.ImageAdjusterApp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqMessageMapper;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqResponse;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.Status;
import org.rcbg.afku.ImageAdjusterApp.exceptions.ImagesLinkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Callbacks {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    WebsocketService websocketService;

    public void receiveProcessedImage(String responseMessage){
        RabbitMqResponse responseObject;
        ProcessedImageDto processedImageDto;
        try {
            responseObject = RabbitMqMessageMapper.JsonStringToResponse(responseMessage);
            processedImageDto = databaseService.saveProcessedImage(responseObject.getProcessedFilename(), responseObject.getRawFilename(), responseObject.getAttributes());
        } catch (JsonProcessingException | ImagesLinkException ex){
            log.error(ex.getClass().getName() + ": " + ex.getMessage());
            log.error("Error: WS message not send due to unidentified user");
            return;
        }
        try {
            websocketService.sendSpecific(responseMessage, processedImageDto.getOwnerUuid());
        } catch (Exception ex){
            log.error("Error: " + ex.getMessage());
            log.error("Cannot send WS message to user " + processedImageDto.getOwnerUuid() + " due to error above");
        }
    }
}
