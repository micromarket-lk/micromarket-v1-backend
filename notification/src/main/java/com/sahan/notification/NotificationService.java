package com.sahan.notification;

import com.sahan.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * The NotificationService class is a Spring Service responsible for handling notification-related operations.
 *
 * This class is annotated with @Service, indicating that it is a service component managed by Spring.
 * The @AllArgsConstructor annotation generates a constructor with all the required dependencies.
 * The dependencies are then injected through this constructor.
 */
@Service
@AllArgsConstructor
public class NotificationService {

    /**
     * The NotificationRepository dependency is injected via the constructor using the @AllArgsConstructor annotation.
     * This ensures that an instance of NotificationRepository is available to this class when it's created.
     */
    private final NotificationRepository notificationRepository;

    /**
     * Method to send a notification based on the provided NotificationRequest.
     * This method saves the notification details into the NotificationRepository.
     *
     * @param notificationRequest The NotificationRequest object representing the notification to be sent.
     */
    public void send(NotificationRequest notificationRequest) {
        // Build a Notification object from the given NotificationRequest and save it using the repository
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerName())
                        .sender("micromarket")
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
