package org.rcbg.afku.ImageAdjusterApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class testController {

    @GetMapping
    public String testEndpoint(HttpServletRequest request){
        return "  { \"_id\": \"6611cc9e335ee6056fc47774\", \"index\": 0 }";
    }
}
