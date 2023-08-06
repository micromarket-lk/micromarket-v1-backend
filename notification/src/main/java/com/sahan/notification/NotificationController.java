package com.sahan.notification;

import com.sahan.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The NotificationController class is a Spring RestController responsible for handling incoming HTTP requests
 * related to sending notifications.
 *
 * This class is annotated with @RestController, indicating that it is a controller that handles RESTful API requests.
 * The base request mapping for all endpoints in this controller is "/api/v1/notification".
 * The @AllArgsConstructor annotation generates a constructor with all the required dependencies.
 * The dependencies are then injected through this constructor.
 * The @Slf4j annotation is used to enable logging capabilities with the SLF4J (Simple Logging Facade for Java) library.
 * The logger variable 'log' will be automatically initialized by Lombok.
 */
@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    /**
     * The NotificationService dependency is injected via the constructor using the @AllArgsConstructor annotation.
     * This ensures that an instance of NotificationService is available to this class when it's created.
     */
    private final NotificationService notificationService;

    /**
     * HTTP POST endpoint for sending notifications.
     * This method is responsible for processing incoming POST requests to "/api/v1/notification" with a JSON payload.
     * The payload will be automatically converted to a NotificationRequest object based on its JSON content.
     *
     * @param notificationRequest The NotificationRequest object representing the notification to be sent.
     */
    @PostMapping
    public void sendNotification(@RequestBody NotificationRequest notificationRequest) {
        // Log the incoming notification
        log.info("New notification... {}", notificationRequest);

        // Delegate the notification sending to the NotificationService
        notificationService.send(notificationRequest);
    }
}