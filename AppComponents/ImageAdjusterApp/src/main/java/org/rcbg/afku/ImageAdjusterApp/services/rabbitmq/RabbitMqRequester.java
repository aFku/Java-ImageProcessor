package org.rcbg.afku.ImageAdjusterApp.services.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class RabbitMqRequester implements IRequester{

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

    @Override
    protected void finalize() throws Throwable { // Using finalize because there is no better way to close connections
        log.debug("Closing connection to RabbitMQ");
        super.finalize();
        this.channel.close();
        this.channel.getConnection().close();
    }
}
