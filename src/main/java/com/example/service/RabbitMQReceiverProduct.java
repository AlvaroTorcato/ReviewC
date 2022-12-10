package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
@RabbitListener(queues = "products2_queue_fanout", id = "listener")
public class RabbitMQReceiverProduct {
    @Autowired
    ProductRepository repository;
    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());
    @RabbitHandler
    public void receiver(Product product) {
        repository.save(product);
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + product.toString());
    }
}