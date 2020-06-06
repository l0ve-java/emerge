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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final TypeReference<ListOf<User>> LIST_USER = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;
    private final NamesService namesService;

    @Value("classpath:/users.json")
    private Resource usersResource;

    private Map<String, User> allUsers;

    @PostConstruct
    @SneakyThrows
    public void initialize() {
        final ListOf<User> data = objectMapper.readValue(usersResource.getURL(), LIST_USER);
        allUsers = Collections.synchronizedMap(data.getList().stream().collect(Collectors.toMap(User::getUserId, Function.identity())));
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

    public User getUserByTicket(String ticket) {
        final Optional<User> user = allUsers.values().stream()
                .filter(u -> ticket.equals(u.getTicket()))
                .findFirst();
        final User result;
        if (user.isEmpty()) {
            final User newUser = createUser(ticket);
            allUsers.put(newUser.getUserId(), newUser);
            result = newUser;
        } else {
            result = user.get();
        }

        return result;
    }

    private User createUser(String ticket) {
        final User user = namesService.getRandomPerson();
        user.setTicket(ticket);
        user.setUserId(UUID.randomUUID().toString());
        return user;
    }

}
