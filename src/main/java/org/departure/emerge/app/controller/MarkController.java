package org.departure.emerge.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.departure.emerge.app.dto.Mark;
import org.departure.emerge.app.dto.response.GetUserMarksResponse;
import org.departure.emerge.app.dto.response.PutMarkResponse;
import org.departure.emerge.app.service.MarkService;

@RestController()
@RequiredArgsConstructor
public class MarkController {
    private final MarkService markService;

    @PutMapping(value = "/api/user/{userId}/section/{sectionId}/mark")
    public PutMarkResponse putMark(@PathVariable String userId, @PathVariable String sectionId, @RequestBody Mark mark) {
        final Mark result = markService.createMark(userId, sectionId, mark.getVideoTimestamp());
        return PutMarkResponse.builder().mark(result).build();
    }

    @GetMapping(value = "/api/user/{userId}/section/{sectionId}/marks")
    public GetUserMarksResponse getUserMarks(@PathVariable String userId, @PathVariable String sectionId) {
        return GetUserMarksResponse.builder().marks(markService.getMarks(userId, sectionId)).build();
    }
}
