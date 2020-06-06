package org.departure.emerge.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendations {
    List<RecommendedTopic> topics = new ArrayList<>();
    List<RecommendedUser> users = new ArrayList<>();
}
