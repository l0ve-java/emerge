package ru.softmachine.odyssey.backend.app.dto;

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
}
