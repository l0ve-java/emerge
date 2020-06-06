package org.departure.emerge.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;
import org.departure.emerge.app.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NamesService {
    private static final TypeReference<List<String>> LIST_STRING = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;

    @Value("classpath:/names.json")
    private Resource namesResource;

    private List<String> names;

    @PostConstruct
    @SneakyThrows
    public void initialize() {
        names = objectMapper.readValue(namesResource.getURL(), LIST_STRING);
    }

    public User getRandomPerson() {
        final int nameI = RandomUtils.nextInt(0, names.size());
        final int picI = RandomUtils.nextInt(20, 99);
        final String pic = String.format(
                nameI > 99 ? "https://randomuser.me/api/portraits/women/%s.jpg" : "https://randomuser.me/api/portraits/men/%s.jpg",
                picI);
        return User.builder()
                .name(names.get(nameI))
                .pic(pic)
                .build();
    }
}
