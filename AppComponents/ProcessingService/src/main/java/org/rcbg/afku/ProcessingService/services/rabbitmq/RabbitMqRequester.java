package org.rcbg.afku.ProcessingService.services.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;

import java.io.IOException;

@Slf4j
@Service
public class RabbitMqRequester {

    private Channel channel;

    @Value("${rabbitmq.queue.requester}")
    String requestQueueName;

    @Autowired
    public RabbitMqRequester(@Qualifier("requesterChannel") Channel requesterChannel){
        this.channel = requesterChannel;
    }

    public void sendMessage(String message) throws IOException {
        log.debug("Sending data to queue: " + this.requestQueueName + " message: " + message);
        channel.basicPublish("", this.requestQueueName, null, message.getBytes());
    }

}
