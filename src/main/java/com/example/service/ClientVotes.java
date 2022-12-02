package com.example.service;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientVotes{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange1;

    public boolean send(int id) {
        boolean response = (boolean) rabbitTemplate.convertSendAndReceive(directExchange1.getName(), "votes", id);

        System.out.println("Got " + response + "");
        return response;
    }

}