package org.rcbg.afku.ImageAdjusterApp.services.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMqService implements IRequester{

    @Autowired
    private RabbitMqRequester rabbitMqRequester;

    @Autowired
    private RabbitMqReceiver rabbitMqReceiver;


    @Override
    public void sendMessage(String message) throws IOException {
        rabbitMqRequester.sendMessage(message);
    }
}
