package com.sahan.core.Services;

import com.sahan.amqp.RabbitMQMessageProducer;
import com.sahan.clients.fraud.FraudCheckResponse;
import com.sahan.clients.fraud.FraudClient;
import com.sahan.clients.notification.NotificationRequest;
import com.sahan.core.Entities.User.User;
import com.sahan.core.Repostitories.UserRepository;
import com.sahan.core.Requests.User.UserGetRequest;
import com.sahan.core.Requests.User.UserRegistrationRequest;
import com.sahan.core.Requests.User.UserUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    /**
     * Registers a new user.
     *
     * @param userRegistrationRequest The request object containing user registration information.
     */
    public void registerUser(@Valid UserRegistrationRequest userRegistrationRequest) {
        // Validate the UserRegistrationRequest
        validateUserRegistrationRequest(userRegistrationRequest);

        // Create a new User object
        User user = User.builder()
                .userName(userRegistrationRequest.userName())
                .firstName(userRegistrationRequest.firstName())
                .lastName(userRegistrationRequest.lastName())
                .email(userRegistrationRequest.email())
                .role(userRegistrationRequest.role().toString())
                .build();

        // Save the user in the database
        userRepository.saveAndFlush(user);

        // Perform fraud check on the user
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(Integer.valueOf(user.getId()));
        log.info(fraudCheckResponse.toString());
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster detected. User registration blocked.");
        }

        // Send a welcome notification to the user via RabbitMQ
        sendWelcomeNotification(user);
    }

    /**
     * Updates an existing user's information.
     *
     * @param userUpdateRequest The request object containing updated user information.
     */
    public void updateUser(@Valid UserUpdateRequest userUpdateRequest) {
        // Validate the UserUpdateRequest
        validateUserUpdateRequest(userUpdateRequest);

        // Find the user by the specified username
        User user = userRepository.findTopUserByUserName(userUpdateRequest.userName());
        if (user == null) {
            throw new IllegalArgumentException("User with the specified username not found.");
        }

        // Update the user's information
        user.setFirstName(userUpdateRequest.firstName());
        user.setLastName(userUpdateRequest.lastName());
        user.setEmail(userUpdateRequest.email());

        // Save the updated user in the database
        userRepository.saveAndFlush(user);

        // Send a notification to the user about the update
        sendUserUpdateNotification(user);
    }

    /**
     * Retrieves a user by username.
     *
     * @param userGetRequest The request object containing the username.
     * @return
     */
    public User getUserByUserName(@Valid UserGetRequest userGetRequest) {
        // Validate the UserGetRequest
        validateUserGetRequest(userGetRequest);

        // Find the user by the specified username
        User user = userRepository.findTopUserByUserName(userGetRequest.userName());
        if (user == null) {
            throw new IllegalArgumentException("User with the specified username not found.");
        }

        // Log the user information
        log.info(user.toString());

        // Send a notification to the user
        sendUserGetNotification(user);
        return user;
    }

    // Helper method to send a welcome notification to the user
    private void sendWelcomeNotification(User user) {
        NotificationRequest notificationRequest = new NotificationRequest(
                Integer.valueOf(user.getId()),
                user.getEmail(),
                String.format("Hi %s, welcome to micromarket...", user.getFirstName())
        );

        rabbitMQMessageProducer.publish(notificationRequest, "internal.exchange",
                "internal.notification.routing-key"
        );
    }

    // Helper method to send a user update notification
    private void sendUserUpdateNotification(User user) {
        NotificationRequest notificationRequest = new NotificationRequest(
                Integer.valueOf(user.getId()),
                user.getEmail(),
                String.format("Hi %s, your user information has been updated.", user.getFirstName())
        );

        rabbitMQMessageProducer.publish(notificationRequest, "internal.exchange",
                "internal.notification.routing-key"
        );
    }

    // Helper method to send a notification for user retrieval
    private void sendUserGetNotification(User user) {
        NotificationRequest notificationRequest = new NotificationRequest(
                Integer.valueOf(user.getId()),
                user.getEmail(),
                String.format("Hi %s, here is your user information.", user.getFirstName())
        );

        rabbitMQMessageProducer.publish(notificationRequest, "internal.exchange",
                "internal.notification.routing-key"
        );
    }

    // Helper method for validating UserRegistrationRequest
    private void validateUserRegistrationRequest(UserRegistrationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("User registration request cannot be null.");
        }

        String firstName = request.firstName();
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty or null.");
        }

        String lastName = request.lastName();
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty or null.");
        }

        String email = request.email();
        if (!isValidEmailFormat(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // Add any other specific validation checks for UserRegistrationRequest here.
    }

    // Helper method for validating UserUpdateRequest
    private void validateUserUpdateRequest(UserUpdateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("User update request cannot be null.");
        }

        String userName = request.userName();
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty or null.");
        }

        String firstName = request.firstName();
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty or null.");
        }

        String lastName = request.lastName();
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty or null.");
        }

        String email = request.email();
        if (!isValidEmailFormat(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // Add any other specific validation checks for UserUpdateRequest here.
    }

    // Helper method for validating UserGetRequest
    private void validateUserGetRequest(UserGetRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("User get request cannot be null.");
        }

        String userName = request.userName();
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty or null.");
        }

        // Add any other specific validation checks for UserGetRequest here.
    }

    // Helper method for email format validation
    private boolean isValidEmailFormat(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false; // Empty or null email is considered invalid
        }

        // Regular expression for basic email validation
        // This pattern covers most common email formats.
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Check if the email matches the regex pattern
        return email.matches(emailRegex);
    }
}
