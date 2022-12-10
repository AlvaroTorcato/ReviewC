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

@EnableRabbit
@Configuration
@EnableAutoConfiguration(exclude = RabbitAutoConfiguration.class)
public class RabbitMQConfig {
    /*
    @Value("${rabbitmq.queue}")
    private String queueName;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingkey;
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.virtualhost}")
    private String virtualHost;
    @Value("${rabbitmq.reply.timeout}")
    private Integer replyTimeout;
    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }
    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setDefaultReceiveQueue(queueName);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setReplyAddress(queue().getName());
        rabbitTemplate.setReplyTimeout(replyTimeout);
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jsonMessageConverter());
        factory.setErrorHandler(errorHandler());
        return factory;
    }
    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
    }
    public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
        private final Logger logger = LogManager.getLogger(getClass());
        @Override
        public boolean isFatal(Throwable t) {
            if (t instanceof ListenerExecutionFailedException) {
                ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
                logger.error("Failed to process inbound message from queue "
                        + lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
                        + "; failed message: " + lefe.getFailedMessage(), t);
            }
            return super.isFatal(t);
        }
    }
    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("products.fanout");
    }


    @Bean
    public RabbitMQSender sender() {
        return new RabbitMQSender();
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }*/

    String reviews1Queue = "reviews1_queue_fanout";

    String products2Queue = "reviews2_queue_fanout";

    String reviewsExchange = "reviews_exchange";

    String reviewsDelete1Queue = "reviewsDelete1_queue_fanout";

    String reviewsDelete2Queue = "reviewsDelete2_queue_fanout";


    String reviewsDeleteExchange = "reviewsDelete_exchange";

    String reviewsUpdate1Queue = "reviewsUpdate1_queue_fanout";

    //String products2Queue = "products2_queue_fanout";

    String reviewsUpdateExchange = "reviewsUpdate_exchange";

    String reviewsVoteUpdate1Queue = "reviewsVoteUpdate1_queue_fanout";

    //String products2Queue = "products2_queue_fanout";

    String reviewsVoteUpdateExchange = "reviewsVoteUpdate_exchange";

    @Bean
    Queue products1Queue() {
        return new Queue(reviews1Queue, true);
    }

    @Bean
    Queue products2Queue() {
        return new Queue(products2Queue, false);
    }

    @Bean
    Queue productsDelete1Queue() {
        return new Queue(reviewsDelete1Queue, true);
    }

    @Bean
    Queue productsDelete2Queue() {
        return new Queue(reviewsDelete2Queue, true);
    }

    /*@Bean
    Queue products2Queue() {
        return new Queue(products2Queue, false);
    }*/
    @Bean
    Queue productsUpdate1Queue() {
        return new Queue(reviewsUpdate1Queue, true);
    }
    @Bean
    Queue productsVoteUpdate1Queue() {
        return new Queue(reviewsVoteUpdate1Queue, true);
    }


    /*@Bean
    Queue products2Queue() {
        return new Queue(products2Queue, false);
    }*/

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(reviewsExchange);
    }
    @Bean
    public FanoutExchange exchangeDelete() {
        return new FanoutExchange(reviewsDeleteExchange);
    }

    @Bean
    public FanoutExchange exchangeUpdate() {
        return new FanoutExchange(reviewsUpdateExchange);
    }
    @Bean
    public FanoutExchange exchangeVoteUpdate() {
        return new FanoutExchange(reviewsVoteUpdateExchange);
    }

    @Bean
    Binding deliveryBinding(Queue products1Queue,@Qualifier("exchange") FanoutExchange exchange) {
        return BindingBuilder.bind(products1Queue).to(exchange);
    }

    @Bean
    Binding emailBinding(Queue products2Queue, FanoutExchange exchange) {
        return BindingBuilder.bind(products2Queue).to(exchange);
    }

    @Bean
    Binding deliveryBindingDelete(Queue productsDelete1Queue,@Qualifier("exchangeDelete") FanoutExchange exchangeDelete) {
        return BindingBuilder.bind(productsDelete1Queue).to(exchangeDelete);
    }
    @Bean
    Binding deliveryBindingDelete1(Queue productsDelete2Queue,@Qualifier("exchangeDelete") FanoutExchange exchangeDelete) {
        return BindingBuilder.bind(productsDelete2Queue).to(exchangeDelete);
    }
    /*@Bean
    Binding emailBinding(Queue products2Queue, FanoutExchange exchange) {
        return BindingBuilder.bind(products2Queue).to(exchange);
    }*/

    @Bean
    Binding deliveryBindingUpdate(Queue productsUpdate1Queue,@Qualifier("exchangeUpdate") FanoutExchange exchangeUpdate) {
        return BindingBuilder.bind(productsUpdate1Queue).to(exchangeUpdate);
    }

    @Bean
    Binding deliveryBindingVoteUpdate(Queue productsVoteUpdate1Queue,@Qualifier("exchangeVoteUpdate") FanoutExchange exchangeVoteUpdate) {
        return BindingBuilder.bind(productsVoteUpdate1Queue).to(exchangeVoteUpdate);
    }

    /*@Bean
    Binding emailBinding(Queue products2Queue, FanoutExchange exchange) {
        return BindingBuilder.bind(products2Queue).to(exchange);
    }*/

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        return cachingConnectionFactory;
   }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jsonMessageConverter());
        factory.setErrorHandler(errorHandler());
        return factory;
    }
    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
    }
    public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
        private final Logger logger = LogManager.getLogger(getClass());
        @Override
        public boolean isFatal(Throwable t) {
            if (t instanceof ListenerExecutionFailedException) {
                ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
                logger.error("Failed to process inbound message from queue "
                        + lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
                        + "; failed message: " + lefe.getFailedMessage(), t);
            }
            return super.isFatal(t);
        }
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}





