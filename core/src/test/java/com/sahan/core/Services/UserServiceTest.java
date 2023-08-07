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
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    // Mock dependencies to be injected into UserService
    @Mock
    private UserRepository userRepository;

    @Mock
    private FraudClient fraudClient;

    @Mock
    private RabbitMQMessageProducer rabbitMQMessageProducer;

    // Injecting the Mock dependencies into UserService
    @InjectMocks
    private UserService userService;

    // Initialize Mockito annotations before each test
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        // Test successful user registration
        UserRegistrationRequest request = new UserRegistrationRequest("John", "Doe", "john.doe@example.com");
        when(fraudClient.isFraudster(anyInt())).thenReturn(new FraudCheckResponse(false));

        userService.registerUser(request);

        verify(userRepository, times(1)).saveAndFlush(any(User.class));
        verify(rabbitMQMessageProducer, times(1)).publish(any(NotificationRequest.class), anyString(), anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterUser_WithInvalidRequest() {
        // Test user registration with invalid request (empty first name)
        UserRegistrationRequest requestWithEmptyFirstName = new UserRegistrationRequest("", "Doe", "john.doe@example.com");

        userService.registerUser(requestWithEmptyFirstName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterUser_WithInvalidEmail() {
        // Test user registration with invalid email format
        UserRegistrationRequest requestWithInvalidEmail = new UserRegistrationRequest("John", "Doe", "invalid.email");

        userService.registerUser(requestWithInvalidEmail);
    }

    @Test(expected = IllegalStateException.class)
    public void testRegisterUser_WithFraudster() {
        // Test user registration with a detected fraudster
        UserRegistrationRequest request = new UserRegistrationRequest("John", "Doe", "john.doe@example.com");
        when(fraudClient.isFraudster(anyInt())).thenReturn(new FraudCheckResponse(true));

        userService.registerUser(request);
    }

    @Test
    public void testUpdateUser_Success() {
        // Test successful user update
        UserUpdateRequest request = new UserUpdateRequest("john_doe", "Updated John", "Updated Doe", "updated.john.doe@example.com");
        User existingUser = new User("john_doe", "John", "Doe", "john.doe@example.com");
        when(userRepository.findTopUserByUserName("john_doe")).thenReturn(existingUser);

        userService.updateUser(request);

        verify(userRepository, times(1)).saveAndFlush(any(User.class));
        verify(rabbitMQMessageProducer, times(1)).publish(any(NotificationRequest.class), anyString(), anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUser_WithNonExistingUser() {
        // Test user update with non-existing user
        UserUpdateRequest request = new UserUpdateRequest("non_existing_user", "Updated John", "Updated Doe", "updated.john.doe@example.com");
        when(userRepository.findTopUserByUserName("non_existing_user")).thenReturn(null);

        userService.updateUser(request);
    }

    @Test
    public void testGetUserByUserName_Success() {
        // Test successful user retrieval
        UserGetRequest request = new UserGetRequest("john_doe");
        User existingUser = new User("john_doe", "John", "Doe", "john.doe@example.com");
        when(userRepository.findTopUserByUserName("john_doe")).thenReturn(existingUser);

        User user = userService.getUserByUserName(request);

        assertNotNull(user);
        assertEquals("john_doe", user.getUserName());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());

        verify(rabbitMQMessageProducer, times(1)).publish(any(NotificationRequest.class), anyString(), anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByUserName_WithInvalidRequest() {
        // Test user retrieval with invalid request (empty username)
        UserGetRequest requestWithEmptyUserName = new UserGetRequest("");

        userService.getUserByUserName(requestWithEmptyUserName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByUserName_WithNonExistingUser() {
        // Test user retrieval with non-existing user
        UserGetRequest request = new UserGetRequest("non_existing_user");
        when(userRepository.findTopUserByUserName("non_existing_user")).thenReturn(null);

        userService.getUserByUserName(request);
    }

    // Add additional tests for other methods if needed
}