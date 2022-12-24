package com.example.service;


import com.example.model.Review;
import com.example.model.Vote;
import com.example.model.VoteUpdate;
import com.example.repository.ReviewRepository;
import com.example.repository.VoteRepository;
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
@RabbitListener(queues = "votesUpdate1_queue_fanout", id = "listener")*/
@Service
public class RabbitMQReceiver {
    @Autowired
    ReviewService service;

    @Autowired
    VoteRepository repository;
    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());
    /*@RabbitHandler
    public void receiver(Vote vote) {
        repository.save(vote);
        boolean n= vote.isVote();
        String aux = vote.setString(n);
        service.updateReviewWithVote(vote.getId(), aux);
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + vote);
    }*/
    @RabbitListener(queues= "#{autoDeleteQueueVote.name}")
    public void consumeJsonMessage(String str)throws JsonProcessingException {
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + str);

        ObjectMapper objectMapper = new ObjectMapper();
        Vote obj=objectMapper.readValue(str, Vote.class);


        //jwtService.createJWT(obj);

        repository.save(obj);
        boolean n= obj.isVote();
        String aux = obj.setString(n);
        service.updateReviewWithVote(obj.getId(),aux);
    }
}
