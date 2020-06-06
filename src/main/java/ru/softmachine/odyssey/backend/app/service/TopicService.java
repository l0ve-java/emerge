package ru.softmachine.odyssey.backend.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.softmachine.odyssey.backend.app.dto.ListOf;
import ru.softmachine.odyssey.backend.app.dto.Mark;
import ru.softmachine.odyssey.backend.app.dto.Topic;

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

    @Value("classpath:/topics.json")
    private Resource topicsResource;

    private Map<String, Topic> allTopics;

    @PostConstruct
    @SneakyThrows
    public void initialize() {
        final ListOf<Topic> data = objectMapper.readValue(topicsResource.getURL(), LIST_TOPIC);
        allTopics = data.getList().stream().collect(Collectors.toMap(Topic::getTopicId, Function.identity()));
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
}
