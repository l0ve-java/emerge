package org.departure.emerge.app.service;

import lombok.RequiredArgsConstructor;
import org.departure.emerge.app.dto.Mark;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkService {
    private final TopicService topicService;

    private List<Mark> allMarks = new ArrayList<>();

    public Mark createMark(String userId, String sectionId, Long videoTs) {
        final Mark mark = Mark.builder()
                .markId(UUID.randomUUID().toString())
                .userId(userId)
                .sectionId(sectionId)
                .videoTimestamp(videoTs)
                .build();

        topicService.getTopicForMark(mark).ifPresent(mark::setTopic);

        allMarks.add(mark);
        return mark;
    }

    public List<Mark> getMarks(String userId, String sectionId) {
        return allMarks.stream()
                .filter(m -> m.getUserId().equals(userId) && m.getSectionId().equals(sectionId))
                .collect(Collectors.toList());
    }
}
