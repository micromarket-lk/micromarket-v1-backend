package com.sahan.core.Controllers;

import com.sahan.core.Requests.User.UserRegistrationRequest;
import com.sahan.core.Services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService customerService;
    @PostMapping
    public void registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        log.info("new customer registration {}", userRegistrationRequest.toString());
        customerService.registerUser(userRegistrationRequest);
    }

    // update
    public void updateUser(){}
    // delete
    public void deleteUser(){}
    // get by name
    public void getUser(){}
 }
