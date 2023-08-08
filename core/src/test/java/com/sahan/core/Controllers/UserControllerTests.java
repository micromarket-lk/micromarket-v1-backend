package com.sahan.core.Controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sahan.core.Requests.User.UserRegistrationRequest;
import com.sahan.core.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_ValidRequest_ShouldReturnOk() {
        // Arrange
        UserRegistrationRequest validRequest = new UserRegistrationRequest("JohnDoe", "johndoe@example.com", "password");

        // Act
        ResponseEntity<String> response = userController.registerUser(validRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registration successful.", response.getBody());
    }

    @Test
    public void testRegisterUser_InvalidRequest_ShouldReturnBadRequest() {
        // Arrange
        UserRegistrationRequest invalidRequest = new UserRegistrationRequest("", "invalid_email", "short");

        // Act
        ResponseEntity<String> response = userController.registerUser(invalidRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().startsWith("Invalid request data:"));
    }

    @Test
    public void testRegisterUser_FraudsterDetected_ShouldReturnUnauthorized() {
        // Arrange
        UserRegistrationRequest validRequest = new UserRegistrationRequest("JohnDoe", "johndoe@example.com", "password");
        doThrow(new IllegalStateException("Fraudster detected.")).when(userService).registerUser(validRequest);

        // Act
        ResponseEntity<String> response = userController.registerUser(validRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().startsWith("Fraudster detected."));
    }

    @Test
    public void testRegisterUser_InternalServerError_ShouldReturnInternalServerError() {
        // Arrange
        UserRegistrationRequest validRequest = new UserRegistrationRequest("JohnDoe", "johndoe@example.com", "password");
        doThrow(new RuntimeException("Internal server error.")).when(userService).registerUser(validRequest);

        // Act
        ResponseEntity<String> response = userController.registerUser(validRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().startsWith("An error occurred during user registration."));
    }

    // Similar tests for updateUser and getUserByUserName methods...

}
