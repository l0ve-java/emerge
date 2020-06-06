package org.departure.emerge.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.departure.emerge.app.dto.ListOf;
import org.departure.emerge.app.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final TypeReference<ListOf<User>> LIST_USER = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;

    @Value("classpath:/users.json")
    private Resource usersResource;

    private Map<String, User> allUsers;

    @PostConstruct
    @SneakyThrows
    public void initialize() {
        final ListOf<User> data = objectMapper.readValue(usersResource.getURL(), LIST_USER);
        allUsers = data.getList().stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
    }

    public List<User> getUsersById(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return ids.stream().map(allUsers::get).collect(Collectors.toList());
    }

    public User getUserById(String id) {
        return allUsers.get(id);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(allUsers.values());
    }

}
