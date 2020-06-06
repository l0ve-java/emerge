package ru.softmachine.odyssey.backend.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.softmachine.odyssey.backend.app.dto.GetUsersResponse;
import ru.softmachine.odyssey.backend.app.service.UserService;

import java.util.List;

@RestController()
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/api/users")
    public GetUsersResponse getUsers(@RequestParam List<String> id) {
        return GetUsersResponse.builder().users(userService.getUsersById(id)).build();
    }
}
