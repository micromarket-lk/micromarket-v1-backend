package com.sahan.core.Services;

import com.sahan.amqp.RabbitMQMessageProducer;
import com.sahan.clients.fraud.FraudCheckResponse;
import com.sahan.clients.fraud.FraudClient;
import com.sahan.clients.notification.NotificationRequest;
import com.sahan.core.Repostitories.CustomerRepository;
import com.sahan.core.Entities.Customer;
import com.sahan.core.Requests.CustomerRegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(@Valid CustomerRegistrationRequest customerRegistrationRequest){
        Customer customer = Customer.builder().firstName(customerRegistrationRequest.firstName)
                .lastName(customerRegistrationRequest.lastName)
                .email(customerRegistrationRequest.email)
        .build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        log.info(fraudCheckResponse.toString());
        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s welcome to micromarket...", customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(notificationRequest, "internal.exchange",
                "internal.notification.routing-key"
                );
    }
}
