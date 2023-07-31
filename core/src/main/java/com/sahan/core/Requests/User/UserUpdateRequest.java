package com.sahan.core.Requests.User;

import com.sahan.core.Enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
public record UserUpdateRequest(String userName, String firstName, String lastName, String email, Role role) {
}
