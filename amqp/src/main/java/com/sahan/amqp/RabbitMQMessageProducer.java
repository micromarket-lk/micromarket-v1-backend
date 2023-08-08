package com.sahan.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;


/**
 * The RabbitMQMessageProducer class is a Spring component responsible for publishing messages to RabbitMQ exchanges.
 *
 * This class is annotated with @Component, indicating that it is a Spring-managed bean and enables automatic component scanning.
 * The @AllArgsConstructor annotation generates a constructor with all the required dependencies.
 * The dependencies are then injected through this constructor.
 * The @Slf4j annotation is used to enable logging capabilities with the SLF4J (Simple Logging Facade for Java) library.
 * The logger variable 'log' will be automatically initialized by Lombok.
 */
@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQMessageProducer {

    /**
     * The AmqpTemplate dependency is injected via the constructor using the @AllArgsConstructor annotation.
     * This ensures that an instance of AmqpTemplate is available to this class when it's created.
     */
    public final AmqpTemplate amqpTemplate;

    /**
     * Method to publish a message to a RabbitMQ exchange with the specified routing key.
     *
     * @param payload    The payload of the message to be sent.
     * @param exchange   The name of the RabbitMQ exchange to which the message will be published.
     * @param routingKey The routing key used for message routing within the exchange.
     */
    public void publish(Object payload, String exchange, String routingKey) {
        // Log the message publishing details
        log.info("Publishing to {} using routingKey {}. payload: {}", exchange, routingKey, payload);

        // Publish the message using the AmqpTemplate's convertAndSend method
        amqpTemplate.convertAndSend(exchange, routingKey, payload);

        // Log the successful message publishing
        log.info("Published to {} using routingKey {}. payload: {}", exchange, routingKey, payload);
    }
}





