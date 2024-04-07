package org.rcbg.afku.ImageAdjusterApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageAddRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class MainController {

    @PostMapping
    public String addImageToProcess(HttpServletRequest request, @Valid ImageAddRequestDto attributes, @RequestParam(value = "file")MultipartFile image){
        String template = "{ \"colorConversion\": \"%s\", \"cropHeight\": %d, \"cropWidth\": %d, \"watermark\": %b, \"file_size\": %d}";
        return String.format(template, attributes.getColorConversion(), attributes.getCropHeight(), attributes.getCropWidth(), attributes.isWatermark(), image.getSize());
    }
}
