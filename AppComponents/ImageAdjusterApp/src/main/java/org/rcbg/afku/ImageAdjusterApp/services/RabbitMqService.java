package org.rcbg.afku.ImageAdjusterApp.services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class RabbitMqService{


    private final String queueName;

    private final String host;

    private Channel channel;

    private Connection connection;

    public RabbitMqService(@Value("${rabbitmq.queue}") String queueName, @Value("${rabbitmq.host}") String host, @Value("${rabbitmq.user}") String user, @Value("${rabbitmq.password}") String password){
        this.host = host;
        this.queueName = queueName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.host);
        factory.setUsername(user);
        factory.setPassword(password);
        log.debug("Connecting to RabbitMQ queue: " + queueName + " on host: " + host);
        try {
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            this.channel.queueDeclare(this.queueName, true, false, false, null);
        } catch (IOException | TimeoutException ex) {
            log.error("Server could not connect to RabbitMQ queue: " + queueName + " on host: " + host);
            log.error("Message: " + ex.getMessage());
            System.exit(-1);
        }
        log.info("Connection for queue: " + queueName + " established");
    }

    public void SendMessage(String message) throws IOException {
        log.debug("Sending data to queue: " + this.queueName + " message: " + message);
        channel.basicPublish("", this.queueName, null, message.getBytes());
    }

    @Override
    protected void finalize() throws Throwable { // Using finalize because there is no better way to close connections
        log.debug("Closing connection to RabbitMQ");
        super.finalize();
        this.channel.close();
        this.connection.close();
    }
}
