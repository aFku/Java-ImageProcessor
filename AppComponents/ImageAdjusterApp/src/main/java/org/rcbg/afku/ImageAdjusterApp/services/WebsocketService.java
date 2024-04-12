package org.rcbg.afku.ImageAdjusterApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;


public class WebsocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendSpecific(@Payload String msg, Principal user) throws Exception {
        simpMessagingTemplate.convertAndSendToUser(user.getName(), "/tasks/added_tasks", msg);
    }
}
