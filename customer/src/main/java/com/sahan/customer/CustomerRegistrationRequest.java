package com.sahan.customer;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
class CustomerRegistrationRequest {
    String firstName;
    String lastName;
    String email;
}