package com.sahan.core.Mappers;

import com.sahan.core.DTOs.UserDTO;
import com.sahan.core.Entities.User.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CustomerDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return null;
    }
}
