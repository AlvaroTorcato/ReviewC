package com.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

/*@EnableRabbit
@Configuration
@EnableAutoConfiguration(exclude = RabbitAutoConfiguration.class)*/
@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange directExchangeProduct () {
        return new DirectExchange("product");
    }

    @Bean
    public DirectExchange directExchangeReview () {
        return new DirectExchange("review");
    }
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("jwt");}

    @Bean
    public FanoutExchange fanout () {
        return new FanoutExchange("Rev");
    }
    @Bean
    public Queue autoDeleteQueue() {
        return new AnonymousQueue();
    }
    @Bean
    public Binding binding (FanoutExchange fanout, Queue autoDeleteQueue){
        return BindingBuilder.bind(autoDeleteQueue).to(fanout);
    }

    @Bean
    public FanoutExchange fanoutRevVote () {
        return new FanoutExchange("RevVote");
    }
    @Bean
    public Queue autoDeleteQueueRevVote() {
        return new AnonymousQueue();
    }
    @Bean
    public Binding bindingRevVote (FanoutExchange fanoutRevVote, Queue autoDeleteQueueRevVote){
        return BindingBuilder.bind(autoDeleteQueueRevVote).to(fanoutRevVote);
    }

    @Bean
    public FanoutExchange fanoutDelete () {
        return new FanoutExchange("RevDel");
    }
    @Bean
    public Queue autoDeleteQueueDelete() {
        return new AnonymousQueue();
    }
    @Bean
    public Binding bindingDelete (FanoutExchange fanoutDelete, Queue autoDeleteQueueDelete){
        return BindingBuilder.bind(autoDeleteQueueDelete).to(fanoutDelete);
    }
    @Bean
    public FanoutExchange fanoutUpdate () {
        return new FanoutExchange("RevUpdate");
    }
    @Bean
    public Queue autoDeleteQueueUpdate() {
        return new AnonymousQueue();
    }
    @Bean
    public Binding bindingUpdate (FanoutExchange fanoutUpdate, Queue autoDeleteQueueUpdate){
        return BindingBuilder.bind(autoDeleteQueueUpdate).to(fanoutUpdate);
    }
    @Bean
    public FanoutExchange fanoutVoteUpdate () {
        return new FanoutExchange("RevVoteUpdate");
    }
    @Bean
    public Queue autoDeleteQueueVoteUpdate() {
        return new AnonymousQueue();
    }
    @Bean
    public Binding bindingVoteUpdate (FanoutExchange fanoutVoteUpdate, Queue autoDeleteQueueVoteUpdate){
        return BindingBuilder.bind(autoDeleteQueueVoteUpdate).to(fanoutVoteUpdate);
    }
    @Bean
    public FanoutExchange fanoutAuth () {
        return new FanoutExchange("Auth");
    }
    @Bean
    public Queue autoDeleteQueueAuth() {
        return new AnonymousQueue();
    }
    @Bean
    public Binding bindingAuth (FanoutExchange fanoutAuth, Queue autoDeleteQueueAuth){
        return BindingBuilder.bind(autoDeleteQueueAuth).to(fanoutAuth);
    }
    @Bean
    public FanoutExchange fanoutProduct () {
        return new FanoutExchange("Products");
    }
    @Bean
    public Queue autoDeleteQueueProduct() {
        return new AnonymousQueue();
    }
    @Bean
    public Binding bindingProduct (FanoutExchange fanoutProduct, Queue autoDeleteQueueProduct){
        return BindingBuilder.bind(autoDeleteQueueProduct).to(fanoutProduct);
    }
    @Bean
    public FanoutExchange fanoutVote () {
        return new FanoutExchange("Vote");
    }
    @Bean
    public Queue autoDeleteQueueVote() {
        return new AnonymousQueue();
    }
    @Bean
    public Binding bindingVote (FanoutExchange fanoutVote, Queue autoDeleteQueueVote){
        return BindingBuilder.bind(autoDeleteQueueVote).to(fanoutVote);
    }

    @Bean
    public FanoutExchange fanoutVoteRev2 () {
        return new FanoutExchange("VoteRev2");
    }
    @Bean
    public Queue autoDeleteQueueVoteRev2() {
        return new AnonymousQueue();
    }
    @Bean
    public Binding bindingVoteRev2 (FanoutExchange fanoutVoteRev2, Queue autoDeleteQueueVoteRev2){
        return BindingBuilder.bind(autoDeleteQueueVoteRev2).to(fanoutVoteRev2);
    }

}





