package org.departure.emerge.app.controller;

import lombok.RequiredArgsConstructor;
import org.departure.emerge.app.dto.User;
import org.departure.emerge.app.dto.response.GetUsersResponse;
import org.departure.emerge.app.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/api/users")
    public GetUsersResponse getUsers(@RequestParam List<String> id) {
        return GetUsersResponse.builder().users(userService.getUsersById(id)).build();
    }

    @GetMapping(value = "/api/login/{ticket}")
    public User login(@PathVariable String ticket) {
        return userService.getUserByTicket(ticket);
    }
}
