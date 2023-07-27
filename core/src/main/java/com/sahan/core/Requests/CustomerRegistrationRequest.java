package com.sahan.core.Requests;

import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class CustomerRegistrationRequest {
    public String firstName;
    public String lastName;
    public String email;
}
