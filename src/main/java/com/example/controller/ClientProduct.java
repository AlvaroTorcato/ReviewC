package com.example.controller;

import com.example.model.Product;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientProduct {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchangeProduct;
    public List<Product> send() {
        int n= 0;
        List<Product> response = (List<Product>) rabbitTemplate.convertSendAndReceive(directExchangeProduct.getName(),"product", n);

        System.out.println("Got " + response + "");
        return response;
    }

}
