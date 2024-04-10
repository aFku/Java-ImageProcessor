package org.rcbg.afku.ImageAdjusterApp.configs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.queue.requester}")
    String requestQueueName;

    @Value("${rabbitmq.queue.receiver}")
    String responseQueueName;

    @Value("${rabbitmq.user}")
    String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.host}")
    private String host;

    private ConnectionFactory connectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.host);
        factory.setUsername(this.username);
        factory.setPassword(this.password);
        return factory;
    }

    @Bean(name = "requesterChannel")
    public Channel requesterChannel(){
        Channel channel = null;
        ConnectionFactory factory = this.connectionFactory();
        log.debug("Connecting to RabbitMQ queue: " + this.requestQueueName + " on host: " + this.host);
        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(this.requestQueueName, true, false, false, null);
        } catch (IOException | TimeoutException ex) {
            log.error("Server could not connect to RabbitMQ queue: " + this.requestQueueName + " on host: " + this.host);
            log.error("Message: " + ex.getMessage());
            System.exit(-1);
        }
        log.info("Connection for queue: " + this.requestQueueName + " established");
        return channel;
    }

    @Bean(name = "receiverChannel")
    public Channel receiverChannel(){
        Channel channel = null;
        ConnectionFactory factory = this.connectionFactory();
        log.debug("Connecting to RabbitMQ queue: " + this.responseQueueName + " on host: " + this.host);
        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(this.responseQueueName, true, false, false, null);
        } catch (IOException | TimeoutException ex) {
            log.error("Server could not connect to RabbitMQ queue: " + this.responseQueueName + " on host: " + this.host);
            log.error("Message: " + ex.getMessage());
            System.exit(-1);
        }
        log.info("Connection for queue: " + this.responseQueueName + " established");
        return channel;
    }
}
