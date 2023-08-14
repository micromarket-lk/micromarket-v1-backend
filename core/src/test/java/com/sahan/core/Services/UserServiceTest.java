package com.sahan.core.Services;


import com.sahan.amqp.RabbitMQMessageProducer;
import com.sahan.clients.fraud.FraudCheckResponse;
import com.sahan.clients.fraud.FraudClient;
import com.sahan.clients.notification.NotificationRequest;
import com.sahan.core.Entities.User.User;
import com.sahan.core.Enums.Role;
import com.sahan.core.Repostitories.UserRepository;
import com.sahan.core.Requests.User.UserGetRequest;
import com.sahan.core.Requests.User.UserRegistrationRequest;
import com.sahan.core.Requests.User.UserUpdateRequest;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sahan.core.Enums.Role.CUSTOMER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest extends MockitoExtension{





//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private FraudClient fraudClient;
//
//    @Mock
//    private RabbitMQMessageProducer rabbitMQMessageProducer;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    public void testRegisterUser_Success() {
//        UserRegistrationRequest request = new UserRegistrationRequest("JohnDoe", "john", "doe", "johndoe@gmail.com", CUSTOMER);
//
//        // Set up a mock behavior for the fraudClient's isFraudster method
//        // Simulates a scenario where the fraud check indicates no fraud detected
//        when(fraudClient.isFraudster(anyInt())).thenReturn(new FraudCheckResponse(false));
//
//        // Call the method under test: userService.registerUser
//        userService.registerUser(request);
//
//        // Verify that the user data is saved to the userRepository
//        // The saveAndFlush method should be invoked exactly once with any User instance
//        verify(userRepository, times(1)).saveAndFlush(any(User.class));
//
//        // Verify that a notification is published using the rabbitMQMessageProducer
//        // The publish method should be invoked exactly once with specific arguments
//        verify(rabbitMQMessageProducer, times(1)).publish(any(NotificationRequest.class), anyString(), anyString());
//
//    }
//
////    @Test
////    public void testRegisterUser_WithInvalidRequest() {
////        // Test user registration with invalid request (empty first name)
////        UserRegistrationRequest requestWithEmptyFirstName = new UserRegistrationRequest("", "Doe", "john.doe@example.com");
////
////        userService.registerUser(requestWithEmptyFirstName);
////    }
//
//    @Test
//    public void testRegisterUser_WithInvalidEmail() {
//        // Test user registration with invalid email format
//        UserRegistrationRequest requestWithInvalidEmail = new UserRegistrationRequest("JohnDoe", "john", "doe", "jeon@doe.com", CUSTOMER);
//
//        userService.registerUser(requestWithInvalidEmail);
//    }
//
//
//    @Test
//    public void testUpdateUser_Success() {
//        UserUpdateRequest request = new UserUpdateRequest("john_doe", "Updated John", "Updated Doe", "updated.john.doe@example.com", CUSTOMER);
//        User existingUser = getNewUser("JohnDoe", "john", "doe", "jeon@doe", CUSTOMER);
//        when(userRepository.findTopUserByUserName("john_doe")).thenReturn(existingUser);
//
//        userService.updateUser(request);
//
//        verify(userRepository, times(1)).saveAndFlush(any(User.class));
//        verify(rabbitMQMessageProducer, times(1)).publish(any(NotificationRequest.class), anyString(), anyString());
//    }
//
//    @Test
//    public void testUpdateUser_WithNonExistingUser() {
//        UserUpdateRequest request = new UserUpdateRequest("non_existing_user", "Updated John", "Updated Doe", "updated.john.doe@example.com", CUSTOMER);
//        when(userRepository.findTopUserByUserName("non_existing_user")).thenReturn(null);
//
//        userService.updateUser(request);
//    }
//
//    @Test
//    public void testGetUserByUserName_Success() {
//        UserGetRequest request = new UserGetRequest("john_doe");
//        User existingUser = getNewUser("JohnDoe", "john", "doe", "jeon@doe", CUSTOMER);
//        when(userRepository.findTopUserByUserName("john_doe")).thenReturn(existingUser);
//
//        User user = userService.getUserByUserName(request);
//
//        assertNotNull(user);
//        assertEquals("john_doe", user.getUserName());
//        assertEquals("John", user.getFirstName());
//        assertEquals("Doe", user.getLastName());
//        assertEquals("john.doe@example.com", user.getEmail());
//
//        verify(rabbitMQMessageProducer, times(1)).publish(any(NotificationRequest.class), anyString(), anyString());
//    }
//
//    @Test
//    public void testGetUserByUserName_WithInvalidRequest() {
//        UserGetRequest requestWithEmptyUserName = new UserGetRequest("");
//
//        userService.getUserByUserName(requestWithEmptyUserName);
//    }
//
//    @Test
//    public void testGetUserByUserName_WithNonExistingUser() {
//        UserGetRequest request = new UserGetRequest("non_existing_user");
//        when(userRepository.findTopUserByUserName("non_existing_user")).thenReturn(null);
//
//        userService.getUserByUserName(request);
//    }
//
//    public User getNewUser(String userName, String firstName, String lastName, String email, Role role) {
//        return User.builder().userName(userName).firstName(firstName).lastName(lastName).role(role.toString()).email(email).build();
//    }
}