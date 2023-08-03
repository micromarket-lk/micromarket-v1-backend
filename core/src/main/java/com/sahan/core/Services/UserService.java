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

    public void registerUser(@Valid UserRegistrationRequest userRegistrationRequest) {
        User user = User.builder().firstName(userRegistrationRequest.firstName())
                .lastName(userRegistrationRequest.lastName())
                .email(userRegistrationRequest.email())
        .build();
        userRepository.saveAndFlush(user);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(Integer.valueOf(user.getId()));
        log.info(fraudCheckResponse.toString());
        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                Integer.valueOf(user.getId()),
                user.getEmail(),
                String.format("Hi %s welcome to micromarket...", user.getFirstName())
        );

        rabbitMQMessageProducer.publish(notificationRequest, "internal.exchange",
                "internal.notification.routing-key"
                );
    }

    //update
    public void updateUser(@Valid UserUpdateRequest userUpdateRequest){
        User user = userRepository.findTopUserByUserName(userUpdateRequest.userName());
        user.setFirstName(userUpdateRequest.firstName());
        user.setLastName(userUpdateRequest.lastName());
        user.setEmail(userUpdateRequest.email());
        userRepository.saveAndFlush(user);
    }

    //getUserByName
    public void getUserByUserName(@Valid UserGetRequest userGetRequest){
        User user = userRepository.findTopUserByUserName(userGetRequest.userName());
        log.info(user.toString());
    }

}
