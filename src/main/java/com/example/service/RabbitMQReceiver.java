package com.example.service;


import com.example.model.Review;
import com.example.model.Vote;
import com.example.model.VoteUpdate;
import com.example.repository.ReviewRepository;
import com.example.repository.VoteRepository;
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

    @Autowired
    VoteRepository repository;
    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());
    @RabbitHandler
    public void receiver(Vote vote) {
        repository.save(vote);
        boolean n= vote.isVote();
        String aux = vote.setString(n);
        service.updateReviewWithVote(vote.getId(), aux);
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + vote);
    }
}
