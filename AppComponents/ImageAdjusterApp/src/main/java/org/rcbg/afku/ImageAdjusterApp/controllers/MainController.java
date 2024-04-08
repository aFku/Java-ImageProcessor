package org.rcbg.afku.ImageAdjusterApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImage;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageAddRequestDto;
import org.rcbg.afku.ImageAdjusterApp.services.DatabaseService;
import org.rcbg.afku.ImageAdjusterApp.services.RabbitMqService;
import org.rcbg.afku.ImageAdjusterApp.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public String addImageToProcess(HttpServletRequest request, @Valid ImageAddRequestDto attributes, @RequestParam(value = "file")MultipartFile image, Authentication auth){
        String fileName = storageService.saveFile(image);
        RawImage rawImage = databaseService.saveRawImageRecord(fileName, auth.getName());

        String template = "{ \"colorConversion\": \"%s\", \"cropHeight\": %d, \"cropWidth\": %d, \"watermark\": %b, \"fileName\": \"%s\", \"UploadedBy\": \"%s\" }";
        try {
            rabbitMqService.SendMessage(template);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return String.format(template, attributes.getColorConversion(), attributes.getCropHeight(), attributes.getCropWidth(), attributes.isWatermark(), fileName, auth.getName());
    }
}
