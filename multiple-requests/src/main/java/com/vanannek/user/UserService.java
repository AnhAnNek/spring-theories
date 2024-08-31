package com.vanannek.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User login(String username) {
        if (username.isEmpty() || username.isBlank()) {
            return null;
        }
        return new User("Default", 20);
    }
}
