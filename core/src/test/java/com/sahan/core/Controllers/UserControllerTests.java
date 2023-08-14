package com.sahan.core.Controllers;

import static com.sahan.core.Enums.Role.CUSTOMER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sahan.core.Enums.Role;
import com.sahan.core.Requests.User.UserRegistrationRequest;
import com.sahan.core.Services.UserService;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@AllArgsConstructor
public class UserControllerTests {

    private UserService userService;

    private UserController userController;

    @Test
    public void testRegisterUser_ValidRequest_ShouldReturnOk() {
        // Arrange
        UserRegistrationRequest validRequest = new UserRegistrationRequest("JohnDoe", "john", "doe", "jeon@doe", CUSTOMER);

        // Act
        ResponseEntity<String> response = userController.registerUser(validRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registration successful.", response.getBody());
    }

//    @Test
//    public void testRegisterUser_InvalidRequest_ShouldReturnBadRequest() {
//        // Arrange
//        UserRegistrationRequest invalidRequest = new UserRegistrationRequest("", "invalid_email", "short");
//
//        // Act
//        ResponseEntity<String> response = userController.registerUser(invalidRequest);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertTrue(response.getBody().startsWith("Invalid request data:"));
//    }

    @Test
    public void testRegisterUser_FraudsterDetected_ShouldReturnUnauthorized() {
        // Arrange
        UserRegistrationRequest validRequest = new UserRegistrationRequest("JohnDoe", "john", "doe", "jeon@doe", CUSTOMER);
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
        UserRegistrationRequest validRequest = new UserRegistrationRequest("JohnDoe", "john", "doe", "jeon@doe", CUSTOMER);
        doThrow(new RuntimeException("Internal server error.")).when(userService).registerUser(validRequest);

        // Act
        ResponseEntity<String> response = userController.registerUser(validRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().startsWith("An error occurred during user registration."));
    }
}
