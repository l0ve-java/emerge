package ru.softmachine.odyssey.backend.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.softmachine.odyssey.backend.app.dto.Mark;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserMarksResponse {
    List<Mark> marks;
}
