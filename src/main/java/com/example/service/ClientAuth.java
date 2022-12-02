package com.example.service;


import com.example.model.UserDetailsDTO;
import org.apache.catalina.User;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientAuth{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange2;

    public UserDetailsDTO send(String jwt) {
        UserDetailsDTO response = (UserDetailsDTO) rabbitTemplate.convertSendAndReceive(directExchange2.getName(), "auths", jwt);

        System.out.println("Got " + response + "");
        return response;
    }

}