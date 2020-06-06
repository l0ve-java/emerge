package org.departure.emerge.app.service;

import com.google.common.base.Functions;
import lombok.RequiredArgsConstructor;
import org.departure.emerge.app.dto.Coeff;
import org.departure.emerge.app.dto.Mark;
import org.departure.emerge.app.dto.Recommendations;
import org.departure.emerge.app.dto.RecommendedTopic;
import org.departure.emerge.app.dto.Topic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationsService {
    private Double allCoef = 0.5;
    private Double sectionCoef = 0.5;
    private Double userCoef = 2d;
    private Double userWeight = 0.1d;

    private final MarkService markService;
    private final TopicService topicService;
    private final UserService userService;

    public void setCoef(Coeff coef) {
        allCoef = coef.getAllCoef();
        sectionCoef = coef.getSectionCoef();
        userCoef = coef.getUserCoef();
        userWeight = coef.getUserWeight();
    }

    public Recommendations getRecommendations(String userId, String sectionId) {
        final List<Mark> allMarks = markService.getAllMarks();
        final Map<String, Double> allTopicRatings = getRating(allMarks);

        final List<Mark> sectionMarks = markService.getMarks(sectionId);
        final Map<String, Double> sectionRatings = getRating(sectionMarks);

        final ArrayList<Topic> userTopics = new ArrayList<>(markService.getMarks(userId, sectionId)
                .stream().map(Mark::getTopic)
                .collect(Collectors.toMap(Topic::getTopicId, Functions.identity(), (a, b) -> a))
                .values());

        final Map<String, Topic> allTopics = allMarks.stream()
                .map(Mark::getTopic)
                .collect(Collectors.toMap(Topic::getTopicId, Functions.identity(), (a, b) -> a));

        final HashMap<String, Double> resultRating = new HashMap<>();
//        allTopicRatings.forEach((key, value) -> addRating(resultRating, key, value * allCoef));
//        sectionRatings.forEach((key, value) -> addRating(resultRating, key, value * sectionCoef));
        userTopics.forEach(topic -> {
            final String topicId = topic.getTopicId();
            final Double topicRate = allTopicRatings.get(topicId);
            final Double sectionRate = sectionRatings.get(topicId);
            final Double userRate = sectionRatings.get(topicId);
            addRating(resultRating, topicId, topicRate * allCoef);
            addRating(resultRating, topicId, sectionRate * sectionCoef);
            addRating(resultRating, topicId, userRate * userCoef);
            addRating(resultRating, topicId, userWeight);
        });

        final Recommendations recommendations = new Recommendations();
        resultRating.forEach((key, value) ->
                recommendations.getTopics().add(
                        RecommendedTopic.builder()
                                .topic(allTopics.get(key))
                                .rating(value)
                                .markCount(getMarkCount(allMarks, key))
                                .build()));
        recommendations.getTopics().sort(Comparator.comparing(t -> -t.getRating()));
        return recommendations;
    }

    private Map<String, Double> getRating(List<Mark> marks) {
        final Map<String, Long> countMap = marks.stream()
                .collect(Collectors.groupingBy(m -> m.getTopic().getTopicId(), Collectors.counting()));
        return countMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (double) (e.getValue()) / marks.size()));
    }

    private Long getMarkCount(List<Mark> marks, String topicId) {
        return marks.stream()
                .filter(m -> topicId.equals(m.getTopic().getTopicId()))
                .count();
    }

    private void addRating(Map<String, Double> rating, String topicId, Double rate) {
        rating.putIfAbsent(topicId, 0d);
        rating.computeIfPresent(topicId, (k, v) -> v + rate);
    }
}
