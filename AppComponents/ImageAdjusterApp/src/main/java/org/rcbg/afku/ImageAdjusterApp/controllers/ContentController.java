package org.rcbg.afku.ImageAdjusterApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ImageAdjusterApp.exceptions.StreamProcessingException;
import org.rcbg.afku.ImageAdjusterApp.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private StorageService storageService;

    private void prepareResponse(InputStream image, HttpServletResponse response){
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try {
            StreamUtils.copy(image, response.getOutputStream());
        } catch (IOException ex){
            log.error(ex.getMessage());
            throw new StreamProcessingException("Issue occurred while loading stream for response");
        }
    }

    @GetMapping("/raw/{filename}")
    public void serveRawImage(HttpServletRequest request, @PathVariable String filename, HttpServletResponse response){
        InputStream in = storageService.getRawImageStream(filename);
        prepareResponse(in, response);
    }

    @GetMapping("/processed/{filename}")
    public void serveProcessedImage(HttpServletRequest request, @PathVariable String filename, HttpServletResponse response){
        InputStream in = storageService.getProcessedImageStream(filename);
        prepareResponse(in, response);
    }
}
