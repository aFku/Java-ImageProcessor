package org.rcbg.afku.ImageAdjusterApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImage;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageProcessAttributes;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqMessageMapper;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqRequest;
import org.rcbg.afku.ImageAdjusterApp.services.DatabaseService;
import org.rcbg.afku.ImageAdjusterApp.services.RabbitMqService;
import org.rcbg.afku.ImageAdjusterApp.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class MainController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private RabbitMqService rabbitMqService;

    @Autowired
    private DatabaseService databaseService;

    @PostMapping
    public String addImageToProcess(HttpServletRequest request, @Valid ImageProcessAttributes attributes, @RequestParam(value = "file")MultipartFile image, Authentication auth){
        String fileName = storageService.saveFile(image);
        RawImage rawImage = databaseService.saveRawImageRecord(fileName, auth.getName());
        RabbitMqRequest rabbitMqRequest = RabbitMqMessageMapper.createRequestObject(rawImage.getFilename(), attributes);
        String message = "{}";
        try {
            message = RabbitMqMessageMapper.requestObjectToJsonString(rabbitMqRequest);
            rabbitMqService.SendMessage(message);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return message;
    }
}
