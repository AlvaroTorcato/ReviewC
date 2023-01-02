package com.example.service;


import com.example.model.Review;
import com.example.model.Vote;
import com.example.repository.VoteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQReceiverVoteRev {
    @Autowired
    ReviewService service;

    @Autowired
    VoteRepository repository;

    @Autowired
    RabbitMQSenderVote rabbitMQSenderVote;
    private static Logger logger = LogManager.getLogger(RabbitMQReceiverVoteRev.class.toString());

    @RabbitListener(queues= "#{autoDeleteQueueRevVote.name}")
    public void consumeJsonMessage(String str) throws IOException {
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + str);

        ObjectMapper objectMapper = new ObjectMapper();
        Review obj=objectMapper.readValue(str, Review.class);


        //jwtService.createJWT(obj);

        //repository.save(obj);
        service.createReview(obj);
        Vote vote;
        if (obj.getRating()==5)vote=new Vote(true, obj.getId(), obj.getUserid());
        else vote=new Vote(false, obj.getId(), obj.getUserid());
        rabbitMQSenderVote.send(vote);
    }
}
