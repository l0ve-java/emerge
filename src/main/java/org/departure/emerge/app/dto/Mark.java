package org.departure.emerge.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    String markId;
    String sectionId;
    String userId;
    Long videoTimestamp;
    Topic topic;
}
