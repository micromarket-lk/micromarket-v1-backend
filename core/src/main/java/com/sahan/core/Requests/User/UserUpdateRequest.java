package com.sahan.core.Requests.User;

import com.sahan.core.Enums.Role;
import lombok.Getter;
import lombok.Setter;

public record UserUpdateRequest(String userName, String firstName, String lastName, String email, Role role) {
    @Override
    public String userName() {
        return userName;
    }

    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public Role role() {
        return role;
    }
}
