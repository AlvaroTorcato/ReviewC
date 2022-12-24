package com.example.service;
import com.example.model.Change;
import com.example.model.JWT;
import com.example.model.Review;
import com.example.model.Votes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RabbitMQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public String exchange = "Rev";
    public String exchangeDelete = "RevDelete";
    public String exchangeUpdate = "RevUpdate";
    public String exchangeVoteUpdate = "ReveVoteUpdate";

    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());
    public void sendJsonMessage(Review review) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String Json=ow.writeValueAsString(review);
        rabbitTemplate.convertAndSend(exchange,"",Json);
        logger.info("Sending Message to the Queue : " + Json);
    }
    public void sendDelete(int id){
        rabbitTemplate.convertAndSend(exchangeDelete,"", id);
        logger.info("Sending Message to the Queue : " + id);
    }

    public void sendUpdate(Change change) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String Json=ow.writeValueAsString(change);
        rabbitTemplate.convertAndSend(exchangeUpdate,"", Json);
        logger.info("Sending Message to the Queue : " + Json);
    }

    public void sendVoteUpdate(Votes vote) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String Json=ow.writeValueAsString(vote);
        rabbitTemplate.convertAndSend(exchangeVoteUpdate,"", Json);
        logger.info("Sending Message to the Queue : " + Json);
    }
    /*@Autowired
    private AmqpTemplate amqpTemplate;

    public String exchange = "reviews_exchange";
    public String exchangeDelete = "reviewsDelete_exchange";
    public String exchangeUpdate = "reviewsUpdate_exchange";
    public String exchangeVoteUpdate = "reviewsVoteUpdate_exchange";
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

    public void sendVoteUpdate(Votes vote){
        amqpTemplate.convertAndSend(exchangeVoteUpdate,"", vote);
        logger.info("Sending Message to the Queue : " + vote);
    }*/
}
