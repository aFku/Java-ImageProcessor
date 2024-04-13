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
        log.error("Debug1"); // Debug
        try {
            responseObject = RabbitMqMessageMapper.JsonStringToResponse(responseMessage);
            //processedImageDto = databaseService.saveProcessedImage(responseObject.getProcessedFilename(), responseObject.getRawFilename(), responseObject.getAttributes());
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
            return ; }// Send error over websocket
//        } catch (ImagesLinkException ex) {
//            ex.printStackTrace();
//            return; // Send error over websocket
//        }
        if(responseObject.getProcessStatus().getStatus() == Status.FAILURE){
            return; // send error over web socket
        } else {
            try {
                websocketService.sendSpecific("{\"Hello\": \"World\"}", "50c2b847-380d-4b29-a4e7-372bf8d06886");
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
