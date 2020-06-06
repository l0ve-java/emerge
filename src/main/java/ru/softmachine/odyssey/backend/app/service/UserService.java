package ru.softmachine.odyssey.backend.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.softmachine.odyssey.backend.app.dto.GetUsersResponse;
import ru.softmachine.odyssey.backend.app.dto.User;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ObjectMapper objectMapper;
    @Value("classpath:/users.json")
    private Resource usersResource;
    private Map<String, User> users;

    @PostConstruct
    @SneakyThrows
    public void initialize() {
        final GetUsersResponse data = objectMapper.readValue(usersResource.getURL(), GetUsersResponse.class);
        users = data.getUsers().stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }

    public List<User> getUsersById(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return ids.stream().map(users::get).collect(Collectors.toList());
    }

}
