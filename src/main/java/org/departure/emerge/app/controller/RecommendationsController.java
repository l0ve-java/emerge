package org.departure.emerge.app.controller;

import lombok.RequiredArgsConstructor;
import org.departure.emerge.app.dto.Coeff;
import org.departure.emerge.app.dto.Recommendations;
import org.departure.emerge.app.service.RecommendationsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
public class RecommendationsController {
    private final RecommendationsService recommendationsService;

    @GetMapping(value = "/api/user/{userId}/section/{sectionId}/recommendations")
    public Recommendations getRecommendations(@PathVariable String userId, @PathVariable String sectionId) {
        return recommendationsService.getRecommendations(userId, sectionId);
    }

    @PostMapping(value = "/api/coeff")
    public void setCoef(@RequestBody Coeff coef) {
        recommendationsService.setCoef(coef);
    }
}
