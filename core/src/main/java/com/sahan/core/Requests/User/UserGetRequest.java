package com.sahan.core.Requests.User;

import lombok.Getter;

public record UserGetRequest(String userName) {
    @Override
    public String userName() {
        return userName;
    }
}
