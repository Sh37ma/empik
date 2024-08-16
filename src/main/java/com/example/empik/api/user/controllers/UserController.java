package com.example.empik.api.user.controllers;

import com.example.empik.api.user.services.UserService;
import com.example.empik.api.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{login}")
    public User getUserData(@PathVariable String login) {
        return userService.getUserData(login);
    }

}
