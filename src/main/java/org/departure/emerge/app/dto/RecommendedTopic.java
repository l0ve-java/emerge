package org.departure.emerge.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedTopic {
    Topic topic;
    Double rating;
    Long markCount;
    List<Mark> userMarks;
}
