package org.departure.emerge.app.service;

import lombok.RequiredArgsConstructor;
import org.departure.emerge.app.dto.Mark;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkService {
    private final TopicService topicService;

    private List<Mark> allMarks = Collections.synchronizedList(new ArrayList<>());

    public Mark createMark(String userId, String sectionId, Long videoTs) {
        final Mark newMark = Mark.builder()
                .markId(UUID.randomUUID().toString())
                .userId(userId)
                .sectionId(sectionId)
                .videoTimestamp(videoTs)
                .build();

        topicService.getTopicForMark(newMark).ifPresent(newMark::setTopic);

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
}
