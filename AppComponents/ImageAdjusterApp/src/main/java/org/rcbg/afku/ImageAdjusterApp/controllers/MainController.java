package org.rcbg.afku.ImageAdjusterApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageAddRequestDto;
import org.rcbg.afku.ImageAdjusterApp.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class MainController {

    @Autowired
    private StorageService storageService;

    @PostMapping
    public String addImageToProcess(HttpServletRequest request, @Valid ImageAddRequestDto attributes, @RequestParam(value = "file")MultipartFile image){
        String fileName = storageService.saveFile(image);
        String template = "{ \"colorConversion\": \"%s\", \"cropHeight\": %d, \"cropWidth\": %d, \"watermark\": %b, \"fileName\": \"%s\" }";
        return String.format(template, attributes.getColorConversion(), attributes.getCropHeight(), attributes.getCropWidth(), attributes.isWatermark(), fileName);
    }
}
