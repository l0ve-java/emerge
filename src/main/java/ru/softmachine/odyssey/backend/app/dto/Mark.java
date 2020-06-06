package ru.softmachine.odyssey.backend.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    String markId;
    String sectionId;
    String userId;
    Long videoTimestamp;
    Topic topic;
}
