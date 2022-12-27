package com.example.service;

import com.example.model.Votes;
import com.example.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*@EnableRabbit
@Component
@RabbitListener(queues = "reviewsVoteUpdate1_queue_fanout", id = "listener4")*/
@Service
public class ReceiverVoteUpdate {
    @Autowired
    ReviewRepository repository;

    @Autowired
    ReviewService service;
    private static Logger logger = LogManager.getLogger(ReceiverVoteUpdate.class.toString());
    /*@RabbitHandler
    public void receiver(Votes vote) {
        repository.updateReviewWithVote(vote.getId(),vote.getUpVotes(), vote.getDownVotes(), vote.getTotalVotes());
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + vote);
    }*/
    @RabbitListener(queues= "#{autoDeleteQueueVoteUpdate.name}")
    public void consumeJsonMessage(String rev)throws JsonProcessingException {
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + rev);

        ObjectMapper objectMapper = new ObjectMapper();
        Votes obj=objectMapper.readValue(rev, Votes.class);


        //jwtService.createJWT(obj);

        //repository.updateReviewWithVote(obj.getId(), obj.getUpVotes(), obj.getDownVotes(),obj.getTotalVotes());
        service.updateReviewWithVote(obj);
    }
}
