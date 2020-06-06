package ru.softmachine.odyssey.backend.app.dto;

import lombok.Data;

@Data
public class User {
    String userId;
    String name;
    String pic;
    String email;
}
