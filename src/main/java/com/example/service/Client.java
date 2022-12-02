package com.example.service;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Client {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    public boolean send(String sku) {
        boolean response = (boolean) rabbitTemplate.convertSendAndReceive(directExchange.getName(), "roytuts", sku);

        System.out.println("Got " + response + "");
        return response;
    }

}