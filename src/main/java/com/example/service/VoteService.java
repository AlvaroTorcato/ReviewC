package com.example.service;

import com.example.model.UserDetailsDTO;
import com.example.model.VoteDTO;
import com.example.model.VoteUpdate;
import com.example.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class VoteService {
    @Autowired
    private VoteRepository repository;
    @Autowired
    private RequestService service;
    @Autowired
    RabbitMQSender rabbitMQSender;


    @Autowired
    JWTService serviceJWT;

    public List<VoteDTO> searchVotes(int idReview) throws IOException {
        List<VoteDTO> votes = repository.findVotesInReview(idReview);
        if (votes == null){
                throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Vote Not Found");
        }
        return votes;
    }

}
