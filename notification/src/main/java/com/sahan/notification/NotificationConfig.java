package com.sahan.notification;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * The NotificationConfig class is a Spring Configuration class responsible for defining RabbitMQ-related beans,
 * such as exchanges, queues, and bindings required for message processing.
 *
 * This class is annotated with @Configuration, indicating that it contains configuration settings for the Spring application context.
 * The @Getter annotation from Lombok generates getter methods for the private fields.
 */
@Configuration
@Getter
public class NotificationConfig {

    /**
     * The value of the "rabbitmq.exchanges.internal" property is injected into this field using the @Value annotation.
     * It represents the name of the internal exchange to be used for message routing.
     */
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    /**
     * The value of the "rabbitmq.queues.notification" property is injected into this field using the @Value annotation.
     * It represents the name of the queue used for handling notification messages.
     */
    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;

    /**
     * The value of the "rabbitmq.routing-keys.internal-notification" property is injected into this field using the @Value annotation.
     * It represents the routing key that messages will be bound to when sent from the internal exchange to the notification queue.
     */
    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKey;

    /**
     * Bean definition for the internalTopicExchange.
     * This method creates and returns a new TopicExchange bean with the name provided in the "internalExchange" property.
     * The TopicExchange is responsible for routing messages based on matching routing keys.
     *
     * @return The TopicExchange bean with the name specified in the "internalExchange" property.
     */
    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    /**
     * Bean definition for the notificationQueue.
     * This method creates and returns a new Queue bean with the name provided in the "notificationQueue" property.
     * The Queue is responsible for storing messages until they are processed by a consumer.
     *
     * @return The Queue bean with the name specified in the "notificationQueue" property.
     */
    @Bean
    public Queue notificationQueue() {
        return new Queue(this.notificationQueue);
    }

    /**
     * Bean definition for the internalToNotificationBinding.
     * This method creates and returns a new Binding bean that binds the notificationQueue to the internalTopicExchange
     * with the specified internalNotificationRoutingKey.
     * This binding determines how messages are routed from the internal exchange to the notification queue.
     *
     * @return The Binding bean that connects the notificationQueue to the internalTopicExchange with the specified routing key.
     */
    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(this.internalNotificationRoutingKey);
    }
}

