package com.example.service;
import com.example.model.Change;
import com.example.model.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RabbitMQSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public String exchange = "reviews_exchange";
    public String exchangeDelete = "reviewsDelete_exchange";
    public String exchangeUpdate = "reviewsUpdate_exchange";
    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());
    public void send(Review review){
        amqpTemplate.convertAndSend(exchange,"", review);
        logger.info("Sending Message to the Queue : " + review.toString());
    }

    public void sendDelete(int id){
        amqpTemplate.convertAndSend(exchangeDelete,"", id);
        logger.info("Sending Message to the Queue : " + id);
    }

    public void sendUpdate(Change change){
        amqpTemplate.convertAndSend(exchangeUpdate,"", change);
        logger.info("Sending Message to the Queue : " + change);
    }
}