package org.rcbg.afku.ImageAdjusterApp.services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
        try {
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            this.channel.queueDeclare(this.queueName, true, false, false, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void SendMessage(String message) throws IOException {
        channel.basicPublish("", this.queueName, null, message.getBytes());
    }

    @Override
    protected void finalize() throws Throwable { // Using finalize because there is no better way to close connections
        super.finalize();
        this.channel.close();
        this.connection.close();
    }
}
