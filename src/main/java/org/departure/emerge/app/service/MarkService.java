package org.departure.emerge.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;
import org.departure.emerge.app.dto.ListOf;
import org.departure.emerge.app.dto.Mark;
import org.departure.emerge.app.dto.MarkPreset;
import org.departure.emerge.app.dto.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkService {
    private static final TypeReference<ListOf<MarkPreset>> LIST_MARK_PRESET = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;
    private final TopicService topicService;
    @Value("classpath:/marks.json")
    private Resource marksResource;

    private List<Mark> allMarks = Collections.synchronizedList(new ArrayList<>());

    @PostConstruct
    @SneakyThrows
    public void initialize() {
        final ListOf<MarkPreset> data = objectMapper.readValue(marksResource.getURL(), LIST_MARK_PRESET);
        final Map<String, Topic> allTopics = topicService.getAllTopics().stream().collect(Collectors.toMap(Topic::getTopicId, Function.identity()));
        data.getList().forEach(markPreset -> {
            final Topic topic = allTopics.get(markPreset.getTopicId());
            Arrays.stream(markPreset.getUsers().split(","))
                    .forEach(userId -> {
                        allMarks.add(
                                Mark.builder()
                                        .userId(userId)
                                        .markId(UUID.randomUUID().toString())
                                        .sectionId(topic.getSectionId())
                                        .topic(topic)
                                        .videoTimestamp(RandomUtils.nextLong(topic.getBegin(), topic.getEnd()))
                                        .build());
                    });

        });
    }

    public Mark createMark(String userId, String sectionId, Long videoTs) {
        final Mark newMark = Mark.builder()
                .markId(UUID.randomUUID().toString())
                .userId(userId)
                .sectionId(sectionId)
                .videoTimestamp(videoTs)
                .build();

        final Optional<Topic> topic = topicService.getTopicForMark(newMark);
        newMark.setTopic(topic.orElseThrow(() -> new IllegalStateException("Cannot resolve topic")));

        final Optional<Mark> existingMark = allMarks.stream()
                .filter(m ->
                        m.getSectionId().equals(newMark.getSectionId())
                                && m.getUserId().equals(newMark.getUserId())
                                && m.getVideoTimestamp().equals(newMark.getVideoTimestamp())
                ).findAny();

        if (existingMark.isEmpty()) {
            allMarks.add(newMark);
        }

        return existingMark.orElse(newMark);
    }

    public List<Mark> getMarks(String userId, String sectionId) {
        return allMarks.stream()
                .filter(m -> m.getUserId().equals(userId) && m.getSectionId().equals(sectionId))
                .collect(Collectors.toList());
    }

    public List<Mark> getMarks(String sectionId) {
        return allMarks.stream()
                .filter(m -> m.getSectionId().equals(sectionId))
                .collect(Collectors.toList());
    }

    public List<Mark> getAllMarks() {
        return allMarks;
    }
}
