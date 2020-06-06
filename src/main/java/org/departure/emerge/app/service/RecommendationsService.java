package org.departure.emerge.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.departure.emerge.app.dto.Recommendations;
import org.departure.emerge.app.dto.RecommendedTopic;
import org.departure.emerge.app.dto.RecommendedUser;
import org.departure.emerge.app.dto.Topic;
import org.departure.emerge.app.dto.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationsService {
    private final ObjectMapper objectMapper;
    private final TopicService topicService;
    private final UserService userService;


//    @PostConstruct
//    @SneakyThrows
//    public void initialize() {
//        final ListOf<User> data = objectMapper.readValue(usersResource.getURL(), LIST_USER);
//        allUsers = data.getList().stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
//    }

    public Recommendations getRecommendations(String userId) {
        final Recommendations recommendations = new Recommendations();
        final List<Topic> allTopics = topicService.getAllTopics();
        for (int i = 0; i < allTopics.size(); i++) {
            final Topic topic = allTopics.get(i);
            recommendations.getTopics().add(
                    RecommendedTopic.builder()
                            .topic(topic)
                            .rating((double) (100 - i))
                            .build());
        }
        final List<User> allUsers = userService.getAllUsers();
        for (int i = 0; i < allUsers.size(); i++) {
            final User user = allUsers.get(i);
            if (!user.getUserId().equals(userId)) {
                recommendations.getUsers().add(
                        RecommendedUser.builder()
                                .user(user)
                                .rating((double) (100 - i))
                                .build());
            }
        }
        return recommendations;
    }

}
