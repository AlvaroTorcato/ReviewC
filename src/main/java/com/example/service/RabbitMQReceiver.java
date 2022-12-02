package com.example.service;


import com.example.model.Review;
import com.example.model.VoteUpdate;
import com.example.repository.ReviewRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
@RabbitListener(queues = "votesUpdate1_queue_fanout", id = "listener")
public class RabbitMQReceiver {
    @Autowired
    ReviewService service;
    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());
    @RabbitHandler
    public void receiver(VoteUpdate vote) {
        service.updateReviewWithVote(vote.getId(), vote.getStatus());
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + vote);
    }
}
