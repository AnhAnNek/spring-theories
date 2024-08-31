package com.vanannek.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login/{username}")
    public User login(@PathVariable(value = "username") String username){
        System.out.println(Thread.currentThread().getName() + " ----------- " + username + " ----------- " + LocalDateTime.now());
        return userService.login(username);
    }
}
