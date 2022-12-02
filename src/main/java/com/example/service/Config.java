package com.example.service;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Value("${queue.name}")
	private String queueName;

	@Value("${xchange.name}")
	private String directXchangeName;

	@Bean
	public Queue queue() {
		return new Queue(queueName);
	}

	@Bean
	public Queue queue1() {
		return new Queue("votes.rpc.queue");
	}
	@Bean
	public Queue queue2() {
		return new Queue("auths.rpc.queue");
	}

	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(directXchangeName);
	}
	@Bean
	public DirectExchange directExchange1() {
		return new DirectExchange("votes.rpc");
	}

	@Bean
	public DirectExchange directExchange2() {
		return new DirectExchange("auths.rpc");
	}

	@Bean
	public Binding binding(@Qualifier("directExchange") DirectExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with("roytuts");
	}

	@Bean
	public Binding binding1(@Qualifier("directExchange1") DirectExchange exchange, Queue queue1) {
		return BindingBuilder.bind(queue1).to(exchange).with("votes");
	}

	@Bean
	public Binding binding2(@Qualifier("directExchange2") DirectExchange exchange, Queue queue2) {
		return BindingBuilder.bind(queue2).to(exchange).with("auths");
	}

	@Bean
	public Client client() {
		return new Client();
	}

	@Bean
	public ClientVotes client1() {
		return new ClientVotes();
	}

	@Bean
	public ClientAuth client2() {
		return new ClientAuth();
	}


}