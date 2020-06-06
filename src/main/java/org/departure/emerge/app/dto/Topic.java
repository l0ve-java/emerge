package org.departure.emerge.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    String topicId;
    String title;
    String color;
    Long begin;
    Long end;
    String sectionId;
    String authorId;
    User author;
    Chat chat;
}
