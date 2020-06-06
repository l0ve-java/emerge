package org.departure.emerge.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.departure.emerge.app.dto.Mark;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PutMarkResponse {
    Mark mark;
}
