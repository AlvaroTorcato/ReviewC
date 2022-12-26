package com.example.service;

import com.example.model.JWT;
import com.example.model.User;
import com.example.model.Vote;
import com.example.repository.JWTRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/*@EnableRabbit
@Component
@RabbitListener(queues = "authentications2_queue_fanout", id = "listener3")*/
@Service
public class RabbitMQReceiverAuth {
    @Autowired
    JWTRepository repository;
    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());
    /*@RabbitHandler
    public void receiver(JWT jwt) {
        repository.save(jwt);
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + jwt.toString());
    }*/
    @RabbitListener(queues= "#{autoDeleteQueueAuth.name}")
    public void consumeJsonMessage(String str)throws JsonProcessingException {
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + str);

        ObjectMapper objectMapper = new ObjectMapper();
        JWT obj=objectMapper.readValue(str, JWT.class);


        //jwtService.createJWT(obj);

        repository.save(obj);
    }
}
