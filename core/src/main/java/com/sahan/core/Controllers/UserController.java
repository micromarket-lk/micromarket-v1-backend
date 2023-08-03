package com.sahan.core.Controllers;

import com.sahan.core.Requests.User.UserGetRequest;
import com.sahan.core.Requests.User.UserRegistrationRequest;
import com.sahan.core.Requests.User.UserUpdateRequest;
import com.sahan.core.Services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/users/")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("register")
    public void registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        log.info("new customer registration {}", userRegistrationRequest.toString());
        userService.registerUser(userRegistrationRequest);
    }

    // update
    @PutMapping("update")
    public void updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        log.info("customer update {}", userUpdateRequest.toString());
        userService.updateUser(userUpdateRequest);
    }
    // delete
//    @DeleteMapping("delete")
//    public void deleteUser(){
//        log.info("");
//    }
    // get by username
    @GetMapping("get")
    public void getUserByUserName(@Valid @RequestBody UserGetRequest userGetRequest){
        log.info("getting user {}", userGetRequest.userName().toString());
        userService.getUserByUserName(userGetRequest);
    }
 }
