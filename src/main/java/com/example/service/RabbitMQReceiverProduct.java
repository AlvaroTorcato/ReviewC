package com.example.service;

import com.example.model.Product;
import com.example.model.Vote;
import com.example.repository.ProductRepository;
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
@RabbitListener(queues = "products2_queue_fanout", id = "listener2")*/
@Service
public class RabbitMQReceiverProduct {
    @Autowired
    ProductRepository repository;
    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());
    /*@RabbitHandler
    public void receiver(Product product) {
        repository.save(product);
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + product.toString());
    }*/
    @RabbitListener(queues= "#{autoDeleteQueueProduct.name}")
    public void consumeJsonMessage(String str)throws JsonProcessingException {
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + str);

        ObjectMapper objectMapper = new ObjectMapper();
        Product obj=objectMapper.readValue(str, Product.class);


        //jwtService.createJWT(obj);

        repository.save(obj);
    }
}
