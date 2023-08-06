package com.sahan.notification.rabbitmq;

import com.sahan.clients.notification.NotificationRequest;
import com.sahan.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * The NotificationConsumer class is responsible for consuming messages from the RabbitMQ queue
 * and delegating the notification processing to the NotificationService.
 *
 * This class is annotated with @Component, which makes it a Spring-managed bean and enables automatic component scanning.
 * It is also annotated with @AllArgsConstructor, which generates a constructor with all the required dependencies.
 * The dependencies are then injected through this constructor.
 *
 * The @Slf4j annotation is used to enable logging capabilities with the SLF4J (Simple Logging Facade for Java) library.
 * The logger variable 'log' will be automatically initialized by Lombok.
 */
@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {

    /**
     * The NotificationService dependency is injected via the constructor using the @AllArgsConstructor annotation.
     * This ensures that an instance of NotificationService is available to this class when it's created.
     */
    private final NotificationService notificationService;

    /**
     * The RabbitMQ message listener method. It is annotated with @RabbitListener, indicating that it will consume messages
     * from the specified RabbitMQ queue as configured in the application properties file.
     * When a message is received, it is automatically converted to a NotificationRequest object based on its JSON content.
     *
     * @param notificationRequest The NotificationRequest object representing the message received from the queue.
     */
    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(NotificationRequest notificationRequest) {
        // Log the received message
        log.info("Consumed {} from queue", notificationRequest);

        // Delegate the notification processing to the NotificationService
        notificationService.send(notificationRequest);
    }
}