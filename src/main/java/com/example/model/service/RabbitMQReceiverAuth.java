package com.example.model.service;

import com.example.model.JWT;
import com.example.repository.JWTRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@EnableRabbit
@Component
@RabbitListener(queues = "authentications2_queue_fanout", id = "listener")
public class RabbitMQReceiverAuth {
    @Autowired
    JWTRepository repository;
    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());
    @RabbitHandler
    public void receiver(JWT jwt) {
        repository.save(jwt);
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + jwt.toString());
    }
}
