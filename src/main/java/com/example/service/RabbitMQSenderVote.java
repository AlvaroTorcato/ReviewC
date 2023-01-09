package com.example.service;

import com.example.model.Vote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSenderVote {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //private AmqpTemplate amqpTemplate;

    public String exchange = "VoteRev2";
    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());
    public void send(Vote vote) throws JsonProcessingException {
        vote.setId(0);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String Json=ow.writeValueAsString(vote);
        rabbitTemplate.convertAndSend(exchange,"",Json);
        //amqpTemplate.convertAndSend(exchange,"", vote);
        logger.info("Sending Message to the Queue : " + vote.toString());
    }
}
