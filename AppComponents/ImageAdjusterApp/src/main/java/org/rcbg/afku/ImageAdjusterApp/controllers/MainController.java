package org.rcbg.afku.ImageAdjusterApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.rcbg.afku.ImageAdjusterApp.domain.ProcessedImage;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImage;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageProcessAttributes;
import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.RawImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqMessageMapper;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqRequest;
import org.rcbg.afku.ImageAdjusterApp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class MainController {

    @Autowired
    private ImageProcessingFacade imageProcessingFacade;

    @Autowired
    private ImageFetchingService imageFetchingService;

    @PostMapping
    public RabbitMqRequest addImageToProcess(HttpServletRequest request, @Valid ImageProcessAttributes attributes, @RequestParam(value = "file")MultipartFile image, Authentication auth){
        return imageProcessingFacade.receiveRawImage(image, attributes, auth.getName());
    }

    @GetMapping
    public List<ProcessedImageDto> getListOfOwnedProcessedImages(HttpServletRequest request, Authentication auth){
        return imageFetchingService.getListOfProcessedImages(auth.getName());
    }

    @GetMapping
    @RequestMapping("/{filename}")
    public ProcessedImageDto getSingleProcessedImage(HttpServletRequest request, Authentication auth, @PathVariable String filename){
        return imageFetchingService.getSingleProcessedImage(auth.getName(), filename);
    }

    @GetMapping
    @RequestMapping("/raw/{filename}")
    public RawImageDto getSingleRawImage(HttpServletRequest request, Authentication auth, @PathVariable String filename) {
        return imageFetchingService.getSingleRawImage(auth.getName(), filename);
    }
}
