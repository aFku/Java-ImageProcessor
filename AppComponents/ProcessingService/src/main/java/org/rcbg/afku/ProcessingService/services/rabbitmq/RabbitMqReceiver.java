package org.rcbg.afku.ProcessingService.services.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.rcbg.afku.ProcessingService.services.Callbacks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class RabbitMqReceiver {

    @Autowired
    private Callbacks callbacks;

    private String responseQueueName;

    private Channel channel;

    private DeliverCallback getDeliveryCallback() {
        return (consumerTag, delivery) ->{
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.debug("Received message: " + message);
            callbacks.receiveProcessingRequest(message);
        };
    }

    @Autowired
    public RabbitMqReceiver(@Qualifier("receiverChannel") Channel recieverChannel, @Value("${rabbitmq.queue.receiver}") String responseQueueName){
        this.responseQueueName = responseQueueName;
        this.channel = recieverChannel;
        try {
            this.channel.basicConsume(this.responseQueueName, true, getDeliveryCallback(), consumerTag -> {});
        } catch (IOException ex){
            log.error("RabbitMqReceiver was not able to subscribe to queue: " + this.responseQueueName);
            log.error("IOException: " + ex.getMessage());
            System.exit(-1);
        }
    }

}
