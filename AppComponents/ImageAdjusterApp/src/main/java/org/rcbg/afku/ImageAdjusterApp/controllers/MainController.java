package org.rcbg.afku.ImageAdjusterApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageProcessAttributes;
import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.RawImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqRequest;
import org.rcbg.afku.ImageAdjusterApp.responses.*;
import org.rcbg.afku.ImageAdjusterApp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProcessingApprovedResponse> addImageToProcess(HttpServletRequest request, ImageProcessAttributes attributes, @RequestParam(value = "file")MultipartFile image, Authentication auth){
        RabbitMqRequest dto = imageProcessingFacade.receiveRawImage(image, attributes, auth.getName());
        return ResponseFactory.createProcessingApprovedResponse(request.getRequestURI(), HttpStatus.OK, dto);
    }

    @GetMapping
    public ResponseEntity<ProcessedImageListResponse> getListOfOwnedProcessedImages(HttpServletRequest request, Authentication auth){
        List<ProcessedImageDto> dtos = imageFetchingService.getListOfProcessedImages(auth.getName());
        return ResponseFactory.createProcessedImageListResponse(request.getRequestURI(), HttpStatus.OK, dtos);
    }

    @GetMapping("/{processedFilename}")
    public ResponseEntity<ProcessedImageResponse> getSingleProcessedImage(HttpServletRequest request, Authentication auth, @PathVariable String processedFilename){
        ProcessedImageDto dto = imageFetchingService.getSingleProcessedImage(auth.getName(), processedFilename);
        return ResponseFactory.createProcessedImageResponse(request.getRequestURI(), HttpStatus.OK, dto);
    }

    @GetMapping("/raw/{rawFilename}")
    public ResponseEntity<RawImageResponse> getSingleRawImage(HttpServletRequest request, Authentication auth, @PathVariable String rawFilename) {
        RawImageDto dto = imageFetchingService.getSingleRawImage(auth.getName(), rawFilename);
        return ResponseFactory.createRawImageResponse(request.getRequestURI(), HttpStatus.OK, dto);
    }
}
