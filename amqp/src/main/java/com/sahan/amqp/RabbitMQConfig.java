package com.sahan.amqp;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The RabbitMQConfig class is a Spring Configuration class responsible for configuring RabbitMQ-related beans,
 * such as RabbitTemplate and MessageConverter.
 *
 * This class is annotated with @Configuration, indicating that it contains configuration settings for the Spring application context.
 * The @AllArgsConstructor annotation generates a constructor with all the required dependencies.
 * The dependencies are then injected through this constructor.
 */
@Configuration
@AllArgsConstructor
public class RabbitMQConfig {

    /**
     * The ConnectionFactory dependency is injected via the constructor using the @AllArgsConstructor annotation.
     * This ensures that an instance of ConnectionFactory is available to this class when it's created.
     */
    private final ConnectionFactory connectionFactory;

    /**
     * Bean definition for the amqpTemplate.
     * This method creates and returns a new RabbitTemplate bean, which is the core interface for interacting with RabbitMQ.
     * The RabbitTemplate is used for sending and receiving messages.
     * The MessageConverter jacksonConverter() is set to convert messages to/from JSON format.
     *
     * @return The RabbitTemplate bean with the Jackson2JsonMessageConverter message converter.
     */
    public RabbitTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter());
        return rabbitTemplate;
    }

    /**
     * Bean definition for the simpleRabbitListenerContainerFactory.
     * This method creates and returns a new SimpleRabbitListenerContainerFactory bean, which is used for setting up
     * RabbitMQ message listeners for consuming messages from queues.
     * The ConnectionFactory and MessageConverter jacksonConverter() are set for the factory.
     *
     * @return The SimpleRabbitListenerContainerFactory bean with the Jackson2JsonMessageConverter message converter.
     */
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jacksonConverter());
        return factory;
    }

    /**
     * Bean definition for the jacksonConverter.
     * This method creates and returns a new Jackson2JsonMessageConverter bean, which is used for converting messages
     * to/from JSON format. JSON is a common format for message payload serialization in RabbitMQ.
     *
     * @return The Jackson2JsonMessageConverter bean for JSON message conversion.
     */
    @Bean
    public MessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }
}