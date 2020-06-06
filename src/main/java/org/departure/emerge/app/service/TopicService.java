package org.departure.emerge.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.departure.emerge.app.dto.ListOf;
import org.departure.emerge.app.dto.Mark;
import org.departure.emerge.app.dto.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {
    private static final TypeReference<ListOf<Topic>> LIST_TOPIC = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final ChatService chatService;

    @Value("classpath:/topics.json")
    private Resource topicsResource;

    private Map<String, Topic> allTopics;

    @PostConstruct
    @SneakyThrows
    public void initialize() {
        final ListOf<Topic> data = objectMapper.readValue(topicsResource.getURL(), LIST_TOPIC);
        data.getList().forEach(topic -> {
            if (topic.getAuthorId() != null) {
                topic.setAuthor(userService.getUserById(topic.getAuthorId()));
            }
            topic.setChat(chatService.getTopicChat(topic.getTopicId()));
        });
        allTopics = data.getList().stream()
                .collect(Collectors.toMap(Topic::getTopicId, Function.identity()));
    }

    public List<Topic> getTopics(List<String> ids) {
        return new ArrayList<>(allTopics.values());
    }

    public Optional<Topic> getTopicForMark(Mark mark) {
        if (mark.getVideoTimestamp() == null) {
            return Optional.empty();
        }

        return allTopics.values().stream()
                .filter(t -> t.getBegin() <= mark.getVideoTimestamp() && t.getEnd() >= mark.getVideoTimestamp())
                .findFirst();
    }

    public List<Topic> getAllTopics() {
        return new ArrayList<>(allTopics.values());
    }
}
