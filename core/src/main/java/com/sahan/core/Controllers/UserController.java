package com.sahan.core.Controllers;

import com.sahan.core.Entities.User.User;
import com.sahan.core.Requests.User.UserGetRequest;
import com.sahan.core.Requests.User.UserRegistrationRequest;
import com.sahan.core.Requests.User.UserUpdateRequest;
import com.sahan.core.Services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/users/")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        try {
            log.info("New customer registration: {}", userRegistrationRequest.toString());
            userService.registerUser(userRegistrationRequest);
            return ResponseEntity.ok("User registration successful.");
        } catch (IllegalArgumentException e) {
            // If there are validation errors or invalid data in the request
            return ResponseEntity.badRequest().body("Invalid request data: " + e.getMessage());
        } catch (IllegalStateException e) {
            // If there is a fraudster detected during registration
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fraudster detected. User registration blocked.");
        } catch (Exception e) {
            // For any other unexpected errors
            log.error("Error during user registration.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during user registration.");
        }
    }

    /**
     * Endpoint for updating an existing user's information.
     *
     * @param userUpdateRequest The request object containing updated user information.
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            log.info("Customer update: {}", userUpdateRequest.toString());
            userService.updateUser(userUpdateRequest);
            return ResponseEntity.ok("User information updated successfully.");
        } catch (IllegalArgumentException e) {
            // If there are validation errors or invalid data in the request
            return ResponseEntity.badRequest().body("Invalid request data: " + e.getMessage());
        } catch (Exception e) {
            // For any other unexpected errors
            log.error("Error during user update.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during user update.");
        }
    }

    /**
     * Endpoint for retrieving a user by username.
     *
     * @param userGetRequest The request object containing the username.
     */
    @GetMapping("/get")
    public ResponseEntity<User> getUserByUserName(@Valid @RequestBody UserGetRequest userGetRequest) {
        try {
            log.info("Getting user by username: {}", userGetRequest.userName());
            User user = userService.getUserByUserName(userGetRequest);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            // If there are validation errors or invalid data in the request
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // For any other unexpected errors
            log.error("Error during getting user by username.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
